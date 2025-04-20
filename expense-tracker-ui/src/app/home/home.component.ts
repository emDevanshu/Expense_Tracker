import {Component, OnInit} from '@angular/core';
import {Expense} from "../shared/models/expense.model";
import {ExpenseService} from "../services/expense.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  expenses: Expense[] = [];
  categories: string[] = ['Food', 'Entertainment', 'Transport', 'Utilities', 'Health', 'Others'];

  newExpense: Expense = {
    title: '',
    amount: 0,
    category: ''
  };

  constructor(private expenseService : ExpenseService) {
  }

  ngOnInit() {
    this.fetchExpenses();
  }

  fetchExpenses() : void{
    this.expenseService.getExpense().subscribe({
      next : (data) => {
        this.expenses = data;
      },
      error : (err) => {
        console.log("Data not loading");
      }
    })
  }

  addExpense() : void {
    this.expenseService.addExpense(this.newExpense).subscribe({
      next : (addedExpense) => {
        this.expenses.push(addedExpense);
        this.newExpense = { title: '', amount: 0, category: '' };
        this.fetchExpenses();
      },
      error : (err) => {
        console.log("Failed to add expense");
      }
    })
  }

  deleteExpense(index: number) {
    const expenseToBeDeleted = this.expenses[index];
    if(expenseToBeDeleted && expenseToBeDeleted.id) {
      this.expenseService.deleteExpense(expenseToBeDeleted.id).subscribe({
        next : (res) => {
          console.log(res);
          this.expenses.splice(index,1);
        },
        error : (err) => {
          console.log('Error deleting expense:',err);
        }
      })
    }
  }

}
