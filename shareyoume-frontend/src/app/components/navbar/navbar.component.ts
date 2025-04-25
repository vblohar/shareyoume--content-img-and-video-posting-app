import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { TokenStorageService } from '../services/token-storage.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  currentUser: any;

  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.currentUser = this.tokenStorage.getUser();
  }

  logout(): void {
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  }
}