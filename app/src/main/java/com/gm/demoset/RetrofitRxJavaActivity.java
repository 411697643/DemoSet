package com.gm.demoset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rx.Subscriber;

public class RetrofitRxJavaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_rx_java);
        button = (Button) findViewById(R.id.click_me_BN);
        button.setOnClickListener(this);
        result = (TextView) findViewById(R.id.result_TV);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click_me_BN:
                getMovie();
                break;
        }
    }

    private void getMovie() {
//        String baseUrl = "https://api.douban.com/v2/movie/";
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
//        MovieService movieService = retrofit.create(MovieService.class);
////        Observable<MovieEntity> movie = movieService.getMovie(0, 10);
//        movieService.getMovie(0,10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<MovieEntity>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onNext(MovieEntity movieEntity) {
//                result.setText(movieEntity.getTitle());
//            }
//        });
        Subscriber subscriber = new Subscriber<HttpResult<List<Subject>>>(){
            @Override
            public void onCompleted() {
                Toast.makeText(RetrofitRxJavaActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(HttpResult<List<Subject>> listHttpResult) {
                result.setText(listHttpResult.getTitle());
            }
        };
        HttpMethods.getInstance().getMoview(subscriber,0,10);

    }
}
