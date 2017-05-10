import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from './http.service';

import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Notification } from '../model/notification';
import { Consultation } from '../model/consultation';

@Injectable()
export class NotificationService {
  private notificationUrl: string = 'http://localhost:8080/clinic/api/notification/';

  constructor(
    private httpService: HttpService
  ) { }

  getNotifications(): Observable<Notification[]> {
    return this.httpService.get(this.notificationUrl)
      .map((res: Response) => res.json());
  }

  getNewNotifications(): Observable<Notification[]> {
    return Observable.interval(1000).switchMap(() => {
      return this.httpService.get(this.notificationUrl + 'new')
        .map((res: Response) => res.json());
    });
  }

  createNotificationForConsultation(consultation: Consultation): Observable<Notification> {
    return this.httpService.post(this.notificationUrl, consultation)
      .map((res: Response) => res.json());
  }


  updateNotification(notification: Notification){
    return this.httpService.put(this.notificationUrl+notification.id, notification)
      .map((res: Response) => res.json());
  }
}
