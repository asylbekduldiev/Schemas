// users.service.ts
import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/user.model';
import { Group } from '../models/group.model';
import { School } from '../models/school.model';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  http = inject(HttpClient);
  baseUrl = "http://localhost:8080/";

  private usersSubject = new BehaviorSubject<User[]>([]);
  private groupsSubject = new BehaviorSubject<Group[]>([]);
  private schoolsSubject = new BehaviorSubject<School[]>([]);

  users$ = this.usersSubject.asObservable();
  groups$ = this.groupsSubject.asObservable();
  schools$ = this.schoolsSubject.asObservable();

  constructor() {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}api/users`);
  }

  getGroups(): Observable<Group[]> {
    return this.http.get<Group[]>(`${this.baseUrl}api/groups`);
  }

  getSchools(): Observable<School[]> {
    return this.http.get<School[]>(`${this.baseUrl}api/schools`);
  }
}