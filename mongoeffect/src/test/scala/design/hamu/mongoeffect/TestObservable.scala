package design.hamu.mongoeffect

import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger, AtomicReference}

import org.mongodb.scala.{MongoException, Observable, Observer, Subscription}

class TestObservable[T](seq: Seq[T], errorIndex: Int) extends Observable[T] {
  def subscribe(observer: Observer[_ >: T]): Unit = {
    val sourceRef = new AtomicReference(seq)
    val failCountDownRef = new AtomicInteger(errorIndex)
    val isSubscribedRef = new AtomicBoolean(true)
    val hasFailedRef = new AtomicBoolean(false)
    observer.onSubscribe(new Subscription {
      def request(n: Long): Unit = {
        if(isSubscribedRef.get) {
          for (_ <- (1 to n.toInt)) {
            failCountDownRef.getAndUpdate {
              case 0 =>
                observer.onError(new MongoException("test"))
                0
              case i =>
                sourceRef.getAndUpdate {
                  case head +: tail =>
                    observer.onNext(head)
                    tail
                  case Nil => Nil
                }
                i - 1
            }
          }
          if(sourceRef.get.isEmpty && !hasFailedRef.get) observer.onComplete
        }
      }
      def unsubscribe(): Unit = isSubscribedRef.set(false)
      def isUnsubscribed(): Boolean = !isSubscribedRef.get
    })
  }
}

object TestObservable {
  def apply[T](seq: Seq[T], errorIndex: Int): Observable[T] = new TestObservable(seq, errorIndex)
  def apply[T](seq: Seq[T]): Observable[T] = new TestObservable(seq, -1)
}
