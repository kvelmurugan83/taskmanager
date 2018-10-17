import { environment } from "../environments/environment";

const URLS = {
    local: "http://localhost:8080",
    prod: ""
}

export class Urls {
    public static getDomain() {
        if (environment.production) {
            return URLS.prod;
        }
        return URLS.local;
    }
}

export const APIURLS = {
    saveTask: "/api/task/saveTask",
    createTask: "/taskmanager/api/tasks",
    deleteTask: "/taskmanager/api/tasks",
    retrieveAllTasks: "/taskmanager/api/tasks"
}
