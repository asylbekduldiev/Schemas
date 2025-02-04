import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  http = inject(HttpClient);
  baseUrl = "http://localhost:8080/"

  constructor() {}

  getUsers() {
    return this.http.get(`${this.baseUrl}api/users`);
  }
  getGroups(){
    return this.http.get(`${this.baseUrl}api/groups`);
  }
  getSchools(){
    return this.http.get(`${this.baseUrl}api/schools`);
  }
}
