import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * Created by Mr singh on 3/1/2017.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}