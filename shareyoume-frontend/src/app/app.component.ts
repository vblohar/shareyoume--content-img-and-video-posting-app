import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TokenStorageService } from './components/services/token-storage.service';
import { NavbarComponent } from "./components/navbar/navbar.component";
import { CommonModule } from '@angular/common';
import { AuthService } from './components/services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'shareyoume-frontend';
  isLoggedIn = false;
  username?: string;

  constructor(private tokenStorage: TokenStorageService, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe((isLoged) => {
      this.isLoggedIn = !!this.tokenStorage.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorage.getUser();
      this.username = user.username;
    }
    })
  }
}
