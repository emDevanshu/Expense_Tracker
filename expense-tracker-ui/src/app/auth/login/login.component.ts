import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email : String = '';
  password : String = '';
  errorMessage : String = '';

  constructor(private router: Router, private authService : AuthService) {}

  onLogin(): void {
    const isSuccess = this.authService.login(this.email, this.password);
    if (isSuccess) {
      this.router.navigate(['/home']);
    } else {
      this.errorMessage = 'Invalid credentials, please try again!';
    }
  }

}
