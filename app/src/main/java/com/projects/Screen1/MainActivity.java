package com.projects.Screen1;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.projects.DataManagement.User;
import com.projects.DataManagement.UserHelperDB;
import com.projects.Screen2.HomeScreen;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, ChipGroup.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    private UserHelperDB dbAccess;
    private Dialog registerDialog, register2Dialog, sportStatusDialog, guideDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);
        dbAccess = new UserHelperDB(this);
        Button confirm = findViewById(R.id.confirm_btn),
                register = findViewById(R.id.registration_btn),
                tutorial = findViewById(R.id.tutorial_btn);
        ImageButton showPassword = findViewById(R.id.showPassword_ib);
        showPassword.setOnClickListener(this);
        confirm.setOnClickListener(this);
        register.setOnClickListener(this);
        tutorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                login();
                break;
            case R.id.registration_btn:
                startRegister();
                break;
            case R.id.register_btn:
                register1();
                break;
            case R.id.register2_btn:
                register2();
                break;
            case R.id.sportStatus_btn:
                startSportStatus();
                break;
            case R.id.endSportStatus_btn:
                endSportStatus();
                break;
            case R.id.tutorial_btn:
                startGuide();
                break;
            case R.id.userNamePattern_ib:
                showFieldPatternDialog(getString(R.string.field_pattern_username));
                break;
            case R.id.passwordPattern_ib:
                showFieldPatternDialog(getString(R.string.field_pattern_password));
                break;
            case R.id.confirmPattern_ib:
                showFieldPatternDialog(getString(R.string.field_pattern_confirm));
                break;
            case R.id.nextGuideWindow_btn:
                moveToNextGuideWindow();
                break;
            case R.id.dismissGuideWindow_btn:
                dismissGuideWindow();
                break;
            case R.id.showPassword_ib:
            case R.id.showRegPassword_ib:
            case R.id.showConfirm_ib:
                showFieldContent(v.getId());
                break;
        }
    }

    private void dismissGuideWindow() {
        guideDialog.dismiss();
        starterGuide.resetProgress();
    }

    public void showFieldContent(int id){
        switch (id){
            case R.id.showPassword_ib:
                EditText password = findViewById(R.id.password_et);
                password.setTransformationMethod(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()) ?
                        PasswordTransformationMethod.getInstance() :
                HideReturnsTransformationMethod.getInstance());
                break;
            case R.id.showRegPassword_ib:
                EditText regPassword = registerDialog.findViewById(R.id.regPassword_et);
                regPassword.setTransformationMethod(regPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()) ?
                        PasswordTransformationMethod.getInstance() :
                        HideReturnsTransformationMethod.getInstance());
                break;
            case R.id.showConfirm_ib:
                EditText confirm = registerDialog.findViewById(R.id.confirm_et);
                confirm.setTransformationMethod(confirm.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()) ?
                        PasswordTransformationMethod.getInstance() :
                        HideReturnsTransformationMethod.getInstance());
                break;
        }
    }

    private void moveToNextGuideWindow() {
        TextSwitcher textSwitcher = guideDialog.findViewById(R.id.guideWindows_ts);
        Integer i = starterGuide.moveForth();
        if (starterGuide.getProgress().getNext() == null) {
            Button nextBtn = guideDialog.findViewById(R.id.nextGuideWindow_btn);
            nextBtn.setText("סיים הדרכה");
            textSwitcher.setText(getString(i));
            nextBtn.setId(R.id.dismissGuideWindow_btn);
        } else if (i != null)
            textSwitcher.setText(getString(i));
    }

    private StarterGuide starterGuide;

    private void startGuide() {
        guideDialog = new Dialog(this);
        guideDialog.setContentView(R.layout.guide_dialog);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        guideDialog.getWindow().setLayout(width, (int) (height * .5));
        TextSwitcher textSwitcher = guideDialog.findViewById(R.id.guideWindows_ts);
        textSwitcher.setFactory(() -> {
            TextView tv = new TextView(MainActivity.this);
            tv.setTextColor(Color.WHITE);
            tv.setPadding(0, 0, 25, 0);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            return tv;
        });
        textSwitcher.setText(getString(R.string.guide_text1));
        Button nextBtn = guideDialog.findViewById(R.id.nextGuideWindow_btn);
        starterGuide = new StarterGuide();
        nextBtn.setOnClickListener(this);
        guideDialog.show();
    }

    private void showFieldPatternDialog(String explanation) {
        Intent intent = new Intent(this, PatternPopup.class);
        intent.putExtra("explanation", explanation);
        startActivity(intent);
    }

    private void login() {
        EditText username = findViewById(R.id.userName_et), password = findViewById(R.id.password_et);
        String user = username.getText().toString(),
                pass = password.getText().toString();
        if (user.equals("") || pass.equals("")) {
            Toast.makeText(MainActivity.this, "שם המשתמש ו/או הסיסמא חסרים", Toast.LENGTH_SHORT).show();
            return;
        }
        User u = dbAccess.containsUser(user, pass);
        if (u != null) {
            Toast.makeText(MainActivity.this, "ההתחברות למשתמש הסתיימה בהצלחה, ברוכים הבאים!", Toast.LENGTH_SHORT).show();
            Intent success = new Intent(MainActivity.this, HomeScreen.class);
            success.putExtra("Username", user);
            startActivity(success);
        } else
            Toast.makeText(MainActivity.this, "שם המשתמש ו/או הסיסמא אינם תקינים", Toast.LENGTH_SHORT).show();
    }

    private void startRegister() {
        registerDialog = new Dialog(MainActivity.this);
        registerDialog.setContentView(R.layout.registration_dialog);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        registerDialog.getWindow().setLayout((int) (width * .95), height);
        ImageButton fieldPatternUsername = registerDialog.findViewById(R.id.userNamePattern_ib),
                fieldPatternPassword = registerDialog.findViewById(R.id.passwordPattern_ib),
                fieldPatternConfirm = registerDialog.findViewById(R.id.confirmPattern_ib),
                showRegPassword = registerDialog.findViewById(R.id.showRegPassword_ib),
                showConfirm = registerDialog.findViewById(R.id.showConfirm_ib);
        showRegPassword.setOnClickListener(this);
        showConfirm.setOnClickListener(this);
        fieldPatternUsername.setOnClickListener(this);
        fieldPatternPassword.setOnClickListener(this);
        fieldPatternConfirm.setOnClickListener(this);
        registerDialog.show();
        Button registerBtn = registerDialog.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(this);
    }

    private void register1() {
        EditText userET = registerDialog.findViewById(R.id.userName_et),
                passwordET = registerDialog.findViewById(R.id.regPassword_et),
                confirmET = registerDialog.findViewById(R.id.confirm_et);
        String username1 = userET.getText().toString(),
                password1 = passwordET.getText().toString(),
                confirm1 = confirmET.getText().toString();
        if (username1.equals("") || password1.equals("") || confirm1.equals("")) {
            Toast.makeText(MainActivity.this, "נא למלא את כל השדות הריקים", Toast.LENGTH_SHORT).show();
            return;
        }
        byte b = 0;
        String PASSWORD_SPECIAL_CHARS = "@#$%^`<>&+=\"!ºª·#~%&'¿¡€,:;*/+-.=_\\[\\]\\(\\)\\|\\_\\?\\\\";

        /*
        A username has to meet the following rules:
        - It must contain at least one digit
        - It must contain at least one Hebrew letter
        - It must not contain any special characters, or any space characters
        - Its length ranges from 5 to 15
        - it cannot exist in the users database
        */
        if (username1.matches("^(?=.*[\\d])(?=.*[א-ת])(?=.*[^" + PASSWORD_SPECIAL_CHARS + "])(?=\\S+$).{5,15}$") &&
                !(dbAccess.containsUserName(username1)))
            b |= 0x4;

        /*
        A username has to meet the following rules:
        - It must contain at least one digit
        - It must contain at least one English, lowercase letter
        - It must contain at least one English, uppercase letter
        - It must not contain any special characters, or any space characters
        - Its length ranges from 5 to 20
        */
        if (password1.matches("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[^" + PASSWORD_SPECIAL_CHARS + "])(?=\\S+$).{5,20}$"))
            b |= 0x2;

        // this field's text has to be equal to the password
        if (confirm1.equals(password1))
            b |= 0x1;
        if (b == 7) { // b == 0x111
            registerDialog.dismiss();
            startRegister2();
        } else
            Toast.makeText(MainActivity.this, "האינפורמציה שהוזנה אינה תקינה", Toast.LENGTH_SHORT).show();
    }

    private void startRegister2() {
        register2Dialog = new Dialog(MainActivity.this);
        register2Dialog.setContentView(R.layout.registration_dialog2);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = dm.heightPixels;
        register2Dialog.getWindow().setLayout((int) (width * .95), height);
        register2Dialog.show();
        RadioGroup group = register2Dialog.findViewById(R.id.species_rg);
        group.setOnCheckedChangeListener(this);
        SeekBar age_sb = register2Dialog.findViewById(R.id.age_sb),
                height_sb = register2Dialog.findViewById(R.id.height_sb),
                weight_sb = register2Dialog.findViewById(R.id.weight_sb);
        EditText ageET = register2Dialog.findViewById(R.id.age_et),
                heightET = register2Dialog.findViewById(R.id.height_et),
                weightET = register2Dialog.findViewById(R.id.weight_et);
        TextWatcher textWatcherAge = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int progress = Integer.valueOf(ageET.getText().toString());
                    age_sb.setProgress((progress >= age_sb.getMin() && progress <= age_sb.getMax()) ? progress : age_sb.getProgress());
                } catch (Exception exception){}
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
        TextWatcher textWatcherHeight = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                int progress = Integer.valueOf(heightET.getText().toString());
                height_sb.setProgress((progress >= height_sb.getMin() && progress <= height_sb.getMax()) ? progress : height_sb.getProgress());
                } catch (Exception exception){}
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
        TextWatcher textWatcherWeight = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                int progress = Integer.valueOf(weightET.getText().toString());
                weight_sb.setProgress((progress >= weight_sb.getMin() && progress <= weight_sb.getMax()) ? progress : weight_sb.getProgress());
                } catch (Exception exception){}
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
        ageET.addTextChangedListener(textWatcherAge);
        heightET.addTextChangedListener(textWatcherHeight);
        weightET.addTextChangedListener(textWatcherWeight);
        age_sb.setOnSeekBarChangeListener(this);
        height_sb.setOnSeekBarChangeListener(this);
        weight_sb.setOnSeekBarChangeListener(this);
        Button sportStatusBtn = register2Dialog.findViewById(R.id.sportStatus_btn);
        sportStatusBtn.setOnClickListener(this);
        Button register2Btn = register2Dialog.findViewById(R.id.register2_btn);
        register2Btn.setOnClickListener(this);
    }

    private void register2() {
        RadioGroup group = register2Dialog.findViewById(R.id.species_rg);
        RadioButton species = register2Dialog.findViewById(group.getCheckedRadioButtonId());
        SeekBar age_sb = register2Dialog.findViewById(R.id.age_sb),
                height_sb = register2Dialog.findViewById(R.id.height_sb),
                weight_sb = register2Dialog.findViewById(R.id.weight_sb);
        ChipGroup chipGroup = sportStatusDialog.findViewById(R.id.sportStatus_cg);
        Chip chip = sportStatusDialog.findViewById(chipGroup.getCheckedChipId());
        String username = ((EditText) registerDialog.findViewById(R.id.userName_et)).getText().toString(),
        password = ((EditText) registerDialog.findViewById(R.id.regPassword_et)).getText().toString();
        User user = new User(
                username,
                password,
                age_sb.getProgress(),
                User.Gender.values()[group.indexOfChild(species)],
                weight_sb.getProgress(),
                height_sb.getProgress(),
                User.SportStatus.values()[chipGroup.indexOfChild(chip)]
        );
        dbAccess.addUser(user);
        Toast.makeText(MainActivity.this, "ההרשמה הסתיימה בהצלחה, ברוכים הבאים!", Toast.LENGTH_SHORT).show();
        Intent success = new Intent(MainActivity.this, HomeScreen.class);
        success.putExtra("Username", user.getUsername());
        startActivity(success);
    }

    private void startSportStatus() {
        sportStatusDialog = new Dialog(MainActivity.this);
        sportStatusDialog.setContentView(R.layout.sport_status_dialog);
        sportStatusDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        sportStatusDialog.show();
        ChipGroup chipGroup = sportStatusDialog.findViewById(R.id.sportStatus_cg);
        chipGroup.setOnCheckedChangeListener(this);
        Button endSportStatusBtn = sportStatusDialog.findViewById(R.id.endSportStatus_btn);
        endSportStatusBtn.setOnClickListener(this);
    }

    private void endSportStatus() {
        sportStatusDialog.dismiss();
        register2Dialog.show();
        Button register2Btn = register2Dialog.findViewById(R.id.register2_btn);
        register2Btn.setEnabled(true);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.age_sb:
                EditText age = register2Dialog.findViewById(R.id.age_et);
                age.setText("" + progress);
                break;
            case R.id.height_sb:
                EditText height = register2Dialog.findViewById(R.id.height_et);
                height.setText("" + progress);
                break;
            case R.id.weight_sb:
                EditText weight = register2Dialog.findViewById(R.id.weight_et);
                weight.setText("" + progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onCheckedChanged(ChipGroup group, int checkedId) {
        sportStatusDialog.findViewById(R.id.endSportStatus_btn).setEnabled(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Button sportStatusBtn = register2Dialog.findViewById(R.id.sportStatus_btn);
        sportStatusBtn.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}