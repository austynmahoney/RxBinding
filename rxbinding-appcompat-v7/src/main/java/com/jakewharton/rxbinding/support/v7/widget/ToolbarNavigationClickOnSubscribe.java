package com.jakewharton.rxbinding.support.v7.widget;

import android.support.v7.widget.Toolbar;
import android.view.View;
import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

final class ToolbarNavigationClickOnSubscribe implements Observable.OnSubscribe<Void> {
  final Toolbar view;

  ToolbarNavigationClickOnSubscribe(Toolbar view) {
    this.view = view;
  }

  @Override public void call(final Subscriber<? super Void> subscriber) {
    checkUiThread();

    View.OnClickListener listener = new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!subscriber.isUnsubscribed()) {
          subscriber.onNext(null);
        }
      }
    };
    view.setNavigationOnClickListener(listener);

    subscriber.add(new MainThreadSubscription() {
      @Override protected void onUnsubscribe() {
        view.setNavigationOnClickListener(null);
      }
    });
  }
}
