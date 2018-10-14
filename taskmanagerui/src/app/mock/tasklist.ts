import { Task } from '../model/task'

export let TASKS: Task[] = [
  {
    taskId: 1001,
    taskName: 'First Task',
    startDate: '10/06/2018',
    endDate: '10/12/2018',
    priority: 26,
    parentTaskId: 0,
    parentTaskName: "",
    isEnd: false
  },
  {
    taskId: 1002,
    taskName: 'Second Task',
    startDate: '10/10/2018',
    endDate: '10/19/2018',
    priority: 10,
    parentTaskId: 1001,
    parentTaskName: 'First Task',
    isEnd: false
  },
  {
    taskId: 1003,
    taskName: 'Third Task',
    startDate: '10/13/2018',
    endDate: '10/21/2018',
    priority: 16,
    parentTaskId: 1001,
    parentTaskName: 'First Task',
    isEnd: false
  }
];