import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';


import { AppComponent } from './app.component';
import { TaskListComponent } from './task-list/task-list.component';
import { TaskEditComponent } from './task-edit/task-edit.component';
import { HeaderComponent } from './header/header.component';
import { TaskService } from './model/taskservice'
import { TaskManagerService } from './task.manager.service'


const routes: Routes = [
  { path: '', redirectTo: '/viewTask', pathMatch: 'full' },
  { path: 'viewTask', component: TaskListComponent },
  { path: 'addTask', component: TaskEditComponent },
  { path: 'editTask/:id', component: TaskEditComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    TaskListComponent,
    TaskEditComponent,
    HeaderComponent
  ],
  imports: [
    RouterModule.forRoot(
      routes,
      { enableTracing: false } // <-- debugging purposes only
    ),
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule
  ],
  providers: [TaskManagerService, TaskService],
  bootstrap: [AppComponent]
})
export class AppModule { }
