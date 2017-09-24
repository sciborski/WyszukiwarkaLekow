package com.example.dariusz.wyszukiwarkalekow.application.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;



public class UseCaseExecutor {

    public interface Listener<RESULT>{
        void onResult(RESULT result);
        void onError(Exception ex);
    }

    private Handler handler;

    public UseCaseExecutor(Context context) {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public <ARGUMENT,RESULT> void executeUseCase(final UseCase<ARGUMENT,RESULT> useCase, final ARGUMENT argument, final Listener<RESULT> listener ){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final RESULT result = useCase.execute(argument);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onResult(result);
                        }
                    });
                } catch (final Exception ex) {
                    ex.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(ex);
                        }
                    });
                }
            }
        });
        thread.start();
    }

}
