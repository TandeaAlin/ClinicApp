import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

import { AuthService } from '../service/auth.service';
import { NotificationService } from '../service/notification.service';
import { Notification } from '../model/notification';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  providers: [
    NotificationService
  ]
})
export class NavbarComponent implements OnInit {
  private notificationCount: number = 0;
  private notifications: Notification[];
  private polling: boolean = false;

  constructor(
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router
  ) { }

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (this.authService.isDoctor()) {
          this.startPolling();
        }
      }
    });
  }

  private startPolling(): void {
    if (this.polling == false) {
      this.polling = true;
      this.notificationService.getNewNotifications()
        .subscribe((notifications: Notification[]) => {
          if (notifications != null) {
            this.notificationCount = notifications.length;
            this.notifications = notifications;
          } else {
            this.notificationCount = 0;
            this.notifications = null;
          }
        },
        err => {
          this.polling = false;
          this.notifications = null;
          this.notificationCount = 0;
        });
    }
  }

  private markAsRead(notification: Notification) {
    notification.seen = true;
    this.notificationService.updateNotification(notification)
      .subscribe(notification => {
        this.router.navigateByUrl('/consultation/observations/'+notification.consultation.id);
      });
  }
}
