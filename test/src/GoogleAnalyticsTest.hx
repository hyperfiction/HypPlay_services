import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.HitBuilders.AppViewBuilder;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.GmsExtension;
import massive.munit.Assert;
// import com.google.android.gms.analytics.ScreenViewBuilder;

class GoogleAnalyticsTest
{
	var instance:GoogleAnalytics;
	var tracker:Dynamic;

	public function new()
	{
		
	}

	@BeforeClass public function beforeClass()
	{
		instance = GoogleAnalytics.getInstance();
		Assert.isNotNull(instance);

		tracker = instance.newTracker("UA-58723767-1");
		Assert.isNotNull(tracker);
	}

	@Test public function testAppViewBuilder()
	{
		var builder = new AppViewBuilder();
		var result:Dynamic = null;
		var hasException = false;
		try { result = builder.build(); } catch (e:Dynamic){ hasException = true; }

		Assert.isFalse(hasException);
		Assert.isNotNull(result);

		hasException = false;
		try
		{ 
			tracker.setScreenName("screen-name-test");
			tracker.send(result); 
		} 
		catch (e:Dynamic)
		{ 
			hasException = true; 
		}			
		Assert.isFalse(hasException);
	}

	@Test public function testEventBuilder()
	{
		var builder = new EventBuilder("test-cat", "test-action", "test-label");
		var result:Dynamic = null;
		var hasException = false;
		try { result = builder.build(); } catch (e:Dynamic){ hasException = true; }
		Assert.isFalse(hasException);
		Assert.isNotNull(result);

		hasException = false;
		try { tracker.send(result); } catch (e:Dynamic){ hasException = true; }			
		Assert.isFalse(hasException);
	}
}
