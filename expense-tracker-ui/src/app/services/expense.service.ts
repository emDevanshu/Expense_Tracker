import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Expense} from "../shared/models/expense.model";

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private baseUrl = 'http://localhost:9876/api/v1/expense';

  constructor(private http : HttpClient) { }

  getExpense() {
    return this.http.get<Expense[]>(`${this.baseUrl}/show`);
  }

  addExpense(expense : Expense) : Observable<Expense> {
    return this.http.post<Expense>(`${this.baseUrl}/add`, expense);
  }

  deleteExpense(id : number) {
    return this.http.delete(`${this.baseUrl}/delete`, {
      params : { id: id.toString() },
      responseType : "text"
    });
  }
}
