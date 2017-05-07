import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response, Headers } from '@angular/http';

import { JwtHelper } from 'angular2-jwt';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class AuthService {
  private authUrl: string = 'http://localhost:8080/clinic/auth/login/';
  private headers : Headers;

  private jwtHelper: JwtHelper;

  private token: string;
  private name: string;
  private roles: string[];

  constructor(private router:Router,private http:Http) { 
    this.headers = new Headers({ 'Content-Type': 'application/json' });
    this.headers.append('Access-Control-Allow-Origin','*');

    this.jwtHelper = new JwtHelper();
    
    this.token = localStorage.getItem('token');
    if(this.token != null)
      this.decodeJwt();
  }

  public login(username: string, password: string): Observable<Boolean> {
    let body = JSON.stringify(
            {
                'username':username,
                'password':password
            }
        );

    return this.http.post(this.authUrl,body, {headers: this.headers})
        .map( (res: Response) => {
          this.token = res.json().token;

          localStorage.setItem('token',this.token); 
          this.decodeJwt();

          return true;
        })
        .catch( this.handleError);
  }

  public logout(): void{
    localStorage.removeItem('token');
    this.name=undefined;
    this.roles=undefined;
    this.router.navigateByUrl('/');
  }

  public getName(): String{
    return this.name;
  }

  public isAdmin(): Boolean{
    if(!this.isAuthenticated())
      return false;

    return (this.roles.includes('ADMIN')); 
  }

  public isSecretary(): Boolean{
    if(!this.isAuthenticated()){
      return false;
    }
    
    return (this.roles.includes('EMPLOYEE')); 
  }

   public isDoctor(): Boolean{
    if(!this.isAuthenticated()){
      return false;
    }
    
    return (this.roles.includes('DOCTOR')); 
  }

  public isAuthenticated(): Boolean{
    if(this.name == null){
      return false;
    }

    if(this.roles == null){
      return false;
    }

    if(this.jwtHelper.isTokenExpired(this.token)){
      return false;
    }

    return true;
  }

  private decodeJwt(): void {
    let decodedToken = this.jwtHelper.decodeToken(this.token);
    
    this.name = decodedToken.sub;
    this.roles = decodedToken.roles;
  }

  private handleError(error: Response | any) {
    return Observable.throw(error);
  }
}