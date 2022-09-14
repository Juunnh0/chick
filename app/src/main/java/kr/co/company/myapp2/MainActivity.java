package kr.co.company.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    LinearLayout timeText;
    TextView countdownText;
    EditText hour, minute, second;
    Button startButton, cancelButton;

    CountDownTimer countDownTimer;
    boolean timerRunning; //타이머상태
    boolean firstState; //초기상태 확인
    long time = 0;
    long tempTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //인텐트 예시


        countdownText = (TextView) findViewById(R.id.countdown_text);
        timeText = (LinearLayout) findViewById(R.id.time);
        hour = (EditText) findViewById(R.id.hour);
        minute = (EditText) findViewById(R.id.minute);
        second = (EditText) findViewById(R.id.second);
        startButton = (Button) findViewById(R.id.start_btn);
        cancelButton = (Button) findViewById(R.id.cancel_btn);

        countdownText.setVisibility(countdownText.INVISIBLE);
        firstState = true;

        //타이머 시작
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeText.setVisibility(timeText.INVISIBLE);
                countdownText.setVisibility(countdownText.VISIBLE);
                startStop();
            }
        });

        //취소
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeText.setVisibility(timeText.VISIBLE);
                countdownText.setVisibility(countdownText.INVISIBLE);
                firstState = true;
                stopTimer();
                startButton.setText("시작");
            }
        });

        updateTimer();

    }

    //타이머 구현
    private void startTimer(){

        //타이머 시간 설정
        if(firstState){
            //countdownText.setVisibility(countdownText.INVISIBLE);
            String sHour = hour.getText().toString();
            String sMin = minute.getText().toString();
            String sSecond = second.getText().toString();
            time = (Long.parseLong(sHour) * 3600000) + (Long.parseLong(sMin) * 60000) + (Long.parseLong(sSecond) * 1000) + 1000;
        }else{
            time = tempTime;
        }

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                tempTime = l;
                updateTimer();
            }

            @Override
            public void onFinish() {}

        }.start();

        startButton.setText("일시정지");
        timerRunning = true;
        firstState = false;
    }

    //타이머 정지
    private void stopTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        startButton.setText("계속");
    }

    //시간 업데이트
    private void updateTimer(){
        int Hour = (int) tempTime/3600000;
        int Minutes = (int) tempTime % 3600000 / 60000;
        int Seconds = (int) tempTime % 3600000 % 60000 / 1000;

        String timeLeftText = "";
        timeLeftText = "" + Hour + ":";
        if(Minutes < 10) timeLeftText += "0";
        timeLeftText += Minutes + ":";

        if(Seconds < 10) timeLeftText += "0";
        timeLeftText += Seconds;

        countdownText.setText(timeLeftText);
    }

    //타이머 상태에 따른 시작, 정지
    private void startStop(){
        if(timerRunning){
            stopTimer();
        }else{
            startTimer();;
        }
    }
}