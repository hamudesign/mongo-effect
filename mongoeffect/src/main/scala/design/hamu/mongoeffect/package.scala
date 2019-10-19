package design.hamu

import fs2._
import cats.implicits._
import cats.effect.ConcurrentEffect
import org.mongodb.scala.{Observable, Observer, Subscription}
import fs2.interop.reactivestreams._
import org.reactivestreams.{
  Publisher,
  Subscriber,
  Subscription => FS2Subscription
}

package object mongoeffect {

  /**
    * Mongo subscription decorator for fs2 conversion
    */
  implicit class RichSubscription(sub: Subscription) {

    /**
      * Convert to fs2 subscription
      * @return fs2 subscription
      */
    def toFS2: FS2Subscription = new FS2Subscription {
      def request(n: Long): Unit = sub.request(n)
      def cancel(): Unit = sub.unsubscribe
    }
  }

  /**
    * Mongo observable decorator for fs2 conversion
    */
  implicit class RichObservable[T](observable: Observable[T]) {

    /**
      * Head option operation wrapped in effect type
      * @tparam F Effect type
      * @return Head option in effect type
      */
    def headOptF[F[_]: ConcurrentEffect]: F[Option[T]] =
      for {
        ls <- observable.toUnicastPublisher.toStream[F].head.compile.toList
      } yield ls.headOption

    /**
      * Head operation wrapped in effect type
      *
      * @tparam F Effect type
      * @return Head in effect type
      */
    def headF[F[_]: ConcurrentEffect]: F[T] =
      for {
        ls <- observable.toUnicastPublisher.toStream[F].head.compile.toList
      } yield ls.head

    /**
      * Convert to fs2 publisher
      * @return FS2 publisher
      */
    def toUnicastPublisher: Publisher[T] = (s: Subscriber[_ >: T]) => {
      observable.subscribe(new Observer[T] {
        override def onSubscribe(sub: Subscription): Unit = {
          s.onSubscribe(sub.toFS2)
        }
        def onNext(result: T): Unit = s.onNext(result)
        def onError(e: Throwable): Unit = s.onError(e)
        def onComplete(): Unit = s.onComplete
      })
    }

    /**
      * Convert to FS2 stream
      * @tparam F Effect type
      * @return FS2 stream
      */
    def toStream[F[_]: ConcurrentEffect]: Stream[F, T] = {
      toUnicastPublisher.toStream[F]
    }
  }
}
