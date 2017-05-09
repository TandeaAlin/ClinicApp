import { Component, OnInit } from '@angular/core';

import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  private notificationCount: number = 1;

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
  }

}
