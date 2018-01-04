package danielfranke.com.cst2335_androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        MessageFragment messageFragment = new MessageFragment();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        messageFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.portraitFrameLayout, messageFragment);
        ft.commit();
    }
}