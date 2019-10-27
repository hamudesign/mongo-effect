package design.hamu.mongoeffect

import org.mongodb.scala.Subscription
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SubscriptionSpec extends AnyWordSpec with Matchers with AsyncMockFactory {
  "MongoEffect" should {
    "convert a mongo subscription to fs2 subscription" in {
      val subscription = mock[Subscription]
      (subscription.request _) expects (1) returning ()
      (subscription.request _) expects (2) returning ()
      (subscription.unsubscribe _).expects()
      (subscription.isUnsubscribed _).expects().returning(true)
      val fs2 = subscription.toFS2
      fs2.request(1)
      fs2.request(2)
      fs2.cancel
      subscription.isUnsubscribed must equal(true)
    }
  }
}
