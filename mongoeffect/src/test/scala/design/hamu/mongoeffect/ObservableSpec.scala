package design.hamu.mongoeffect

import java.util.concurrent.atomic.AtomicReference

import cats.effect.IO
import cats.effect.scalatest.AsyncIOSpec
import org.mongodb.scala.MongoException
import org.reactivestreams.{Publisher, Subscriber, Subscription}
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.Inside
import org.scalatest.matchers.must.Matchers

class ObservableSpec
    extends AsyncIOSpec
    with Matchers
    with AsyncMockFactory
    with Inside {
  "MongoUtil" - {
    "should convert an empty mongo observable into a unicast publisher" in {
      val publisher = TestObservable[Int](Nil).toUnicastPublisher
      val subscriber = mock[Subscriber[Int]]
      (subscriber.onSubscribe _) expects (*) onCall (
          (s: Subscription) => s.request(1)
      )
      (subscriber.onComplete _) expects () returning ()
      publisher.subscribe(subscriber)
      IO(publisher mustBe a[Publisher[_]])
    }
    "should convert a non-empty mongo observable into a unicast publisher" in {
      val publisher = TestObservable(List(1, 2, 3, 4)).toUnicastPublisher
      val subscriber = mock[Subscriber[Int]]
      val subscriptionRef: AtomicReference[Option[Subscription]] =
        new AtomicReference(None)
      (subscriber.onSubscribe _) expects (*) onCall (
          (sub: Subscription) => subscriptionRef.set(Some(sub))
      )
      (subscriber.onNext _) expects (1) returning ()
      (subscriber.onNext _) expects (2) returning ()
      (subscriber.onNext _) expects (3) returning ()
      (subscriber.onNext _) expects (4) returning ()
      (subscriber.onComplete _) expects () returning ()
      publisher.subscribe(subscriber)
      subscriptionRef.get.get.request(2)
      subscriptionRef.get.get.request(3)
      IO(publisher mustBe a[Publisher[_]])
    }
    "should convert an errorful mongo observable into a unicast publisher" in {
      val publisher = TestObservable(List('a', 'b', 'c'), 2).toUnicastPublisher
      val subscriber = mock[Subscriber[Char]]
      val subscriptionRef: AtomicReference[Option[Subscription]] =
        new AtomicReference(None)
      (subscriber.onSubscribe _) expects (*) onCall (
          (sub: Subscription) => subscriptionRef.set(Some(sub))
      )
      (subscriber.onNext _) expects ('a') returning ()
      (subscriber.onNext _) expects ('b') returning ()
      (subscriber.onError _) expects (*) returning ()
      publisher.subscribe(subscriber)
      subscriptionRef.get.get.request(1)
      subscriptionRef.get.get.request(2)
      IO(publisher mustBe a[Publisher[_]])
    }
    "should convert an empty mongo observable into a fs2 stream" in {
      for {
        ls <- TestObservable[Int](Nil).toStream[IO].compile.toList
      } yield ls must equal(Nil)
    }
    "should convert a non-empty mongo observable into a fs2 stream" in {
      for {
        ls <- TestObservable(List(1, 2, 3, 4)).toStream[IO].compile.toList
      } yield ls must equal(List(1, 2, 3, 4))
    }
    "should convert an errorful mongo observable into a fs2 stream" in {
      for {
        result <- TestObservable(List("foo", "bar"), 1)
          .toStream[IO]
          .compile
          .toList
          .attempt
      } yield inside(result) {
        case Left(e) => e mustBe a[MongoException]
      }
    }
  }
}
