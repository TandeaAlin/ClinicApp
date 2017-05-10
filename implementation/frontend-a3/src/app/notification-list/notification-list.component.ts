import { Component, OnInit } from '@angular/core';

import { NotificationService } from '../service/notification.service';
import { Notification } from '../model/notification';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.css'],
  providers:[
    NotificationService
  ]
})
export class NotificationListComponent implements OnInit {
  private notifications: Notification[];

  constructor(
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.loadNotifications();
  }

  private loadNotifications(): void{
    this.notificationService.getNotifications()
      .subscribe( notifications => this.notifications = notifications)
  }

}
