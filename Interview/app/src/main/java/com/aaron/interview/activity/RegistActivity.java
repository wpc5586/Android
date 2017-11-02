package com.aaron.interview.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.aaron.aaronlibrary.bean.BaseBean;
import com.aaron.aaronlibrary.http.BaseMap;
import com.aaron.aaronlibrary.http.PostCall;
import com.aaron.aaronlibrary.http.ServerUrl;
import com.aaron.aaronlibrary.utils.VerifyUtils;
import com.aaron.interview.R;
import com.aaron.interview.base.InterViewActivity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 注册页面
 * Created by Aaron on 2017/11/2.
 */

public class RegistActivity extends InterViewActivity {

    private AutoCompleteTextView userIdView;
    private EditText passwordView;
    private EditText pwdConfirmView;
    private EditText phoneView;
    private EditText codeView;
    private Button codeButton;
    private String phone; // 记录用户获取验证码的手机号

    private int countDown = 60;

    /**
     * 是否正在倒计时
     */
    private boolean isCountDowning;

    private EventHandler eventHandler;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void findView() {
        super.findView();
        userIdView = (AutoCompleteTextView) findViewById(R.id.user_id);
        passwordView = (EditText) findViewById(R.id.password);
        pwdConfirmView = (EditText) findViewById(R.id.password_confirm);
        phoneView = (EditText) findViewById(R.id.phone);
        codeView = (EditText) findViewById(R.id.sms_code);
        codeButton = (Button) findAndSetClickListener(R.id.code);
        findAndSetClickListener(R.id.regist);
    }

    @Override
    protected void init() {
        super.init();
        setActionbarVisibility(true);
        setActionbarTitle("注册");
        initSMS();
    }

    private void initSMS() {
        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        SMSSDK.setAskPermisionOnReadContact(true);
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    String msg = throwable.getMessage();
                    showToast(msg);
                } else if (result == SMSSDK.RESULT_COMPLETE){
                    switch (event) {
                        case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressDialog();
                                    showToast("验证码获取成功");
                                    setObtainCode();
                                }
                            });
                            break;
                        case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    submitSuccess();
                                }
                            });
                            break;
                    }
                } else
                    showToast("验证异常");
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 获取验证码
     */
    private void obtainCode() {
        String phone = phoneView.getText().toString();
        if (!VerifyUtils.isPhone(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }
        isCountDowning = true;
        SMSSDK.getVerificationCode("86", phone);
        this.phone = phone;
        showProgressDialog("加载中");
    }

    /**
     * 设置获取验证码按钮状态
     */
    private void setObtainCode() {
        final String codeText = "重新获取(";
        if (!isCountDowning)
            return;
        codeButton.setEnabled(false);
        codeButton.setText(codeText + countDown + ")");
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 60; i++) {
                    if (!isCountDowning)
                        break;
                    try {
                        sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                codeButton.setText(codeText + --countDown + ")");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        countDown = 60;
                        codeButton.setEnabled(true);
                        if (isCountDowning)
                            codeButton.setText("重新获取");
                        else
                            codeButton.setText("获取验证码");
                    }
                });
            }
        }.start();
    }

    /**
     * 验证码验证成功，去服务器注册
     */
    private void submitSuccess() {
        String userId = userIdView.getText().toString();
        String password = passwordView.getText().toString();
        String pwdConfirm = pwdConfirmView.getText().toString();
        String phone = phoneView.getText().toString();
        if (!verifyData(userId, password, pwdConfirm))
            return;
        BaseMap params = new BaseMap();
        params.put("userId", userId);
        params.put("password", password);
        params.put("phone", phone);
        PostCall.post(mContext, ServerUrl.regist(), params, new PostCall.PostResponse<BaseBean>() {
            @Override
            public void onSuccess(int statusCode, byte[] responseBody, BaseBean bean) {
                showToast("注册成功");
                finish();
            }

            @Override
            public void onFailure(int statusCode, byte[] responseBody) {

            }
        }, new String[]{"", ""}, true, BaseBean.class);
    }

    private boolean verifyData(String userId, String password, String pwdConfirm) {
        if (TextUtils.isEmpty(userId)) {
            showToast("用户名不可为空");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            showToast("密码不可为空");
            return false;
        } else if (TextUtils.isEmpty(pwdConfirm)) {
            showToast("确认密码不可为空");
            return false;
        } else if (!password.equals(pwdConfirm)) {
            showToast("两次密码不相同，请重新输入");
            return false;
        } else
            return true;
    }

    /**
     * 注册，先验证验证码是否正确
     */
    private void regist() {
        showProgressDialog("注册中");
        SMSSDK.submitVerificationCode("86", phone, codeView.getText().toString());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.code:
                obtainCode();
                break;
            case R.id.regist:
                regist();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
