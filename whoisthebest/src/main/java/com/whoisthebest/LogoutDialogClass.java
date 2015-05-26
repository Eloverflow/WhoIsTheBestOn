package com.whoisthebest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import library.UserFunctions;

public class LogoutDialogClass extends Dialog implements
View.OnClickListener {

public Activity c;
public Dialog d;
public Button yes, no;

public LogoutDialogClass(Activity a) {
super(a);
// TODO Auto-generated constructor stub
this.c = a;
}

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
requestWindowFeature(Window.FEATURE_NO_TITLE);
setContentView(R.layout.dialog_logout_layout);
yes = (Button) findViewById(R.id.btn_yes);
no = (Button) findViewById(R.id.btn_no);
yes.setOnClickListener(this);
no.setOnClickListener(this);

}

@Override
public void onClick(View v) {
switch (v.getId()) {
case R.id.btn_yes:

    UserFunctions logout = new UserFunctions();
    logout.logoutUser(c.getApplicationContext());
    Intent witbStart = new Intent(c.getApplicationContext(), WitbStart.class);
    witbStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    c.startActivity(witbStart);
    c.finish();

    /*
  c.finish();
  Intent loginIntent = new Intent(c, Welcome.class);
  loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
  c.startActivity(loginIntent);*/

  
  
  break;
case R.id.btn_no:
  dismiss();
  break;
default:
  break;
}
dismiss();
}
}