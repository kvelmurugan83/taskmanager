
import { Injectable } from '@angular/core';
import { Task } from './model/task'
import { Observable } from 'rxjs'
import { of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Urls, APIURLS } from './app.constants';

const httpOptions = {
    headers: new HttpHeaders(
        { 
            'Content-Type': 'application/json', 
            'Access-Control-Allow-Origin': '*' ,
            'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS'
        }
    )
};

@Injectable()
export class TaskManagerService {

    constructor(private http: HttpClient) {
    }

    create(task: Task): Observable<Task> {
        var url = Urls.getDomain().concat(APIURLS.createTask);
        return this.http.post<Task>(url, task, { headers: httpOptions.headers });
    }

    endTask(task: Task) {
        task.end = true;    
        var url = Urls.getDomain().concat(APIURLS.createTask);
        return this.http.put<Task>(url, task, { headers: httpOptions.headers });    
    }

    update(task: Task): Observable<Task> {
        var url = Urls.getDomain().concat(APIURLS.createTask);
        return this.http.put<Task>(url, task, { headers: httpOptions.headers });
    }

    getAllTask(): Observable<Task[]> {
        var url = Urls.getDomain().concat(APIURLS.retrieveAllTasks);
        return this.http.get<Task[]>(url, { headers: httpOptions.headers });
    }
}
