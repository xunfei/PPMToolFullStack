import {
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK,
  UPDATE_SORT
} from "../actions/types";

const initialState = {
  project_tasks: [],
  project_task: {},
  sortBy: "0"
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_BACKLOG:
      return {
        ...state,
        project_tasks: action.payload
      };

    case GET_PROJECT_TASK:
      return {
        ...state,
        project_task: action.payload
      };

    case DELETE_PROJECT_TASK:
      return {
        ...state,
        project_tasks: state.project_tasks.filter(
          project_task => project_task.projectSequence !== action.payload
        )
      };

    case UPDATE_SORT:
      return {
        ...state,
        sortBy: action.payload
      };

    default:
      return state;
  }
}
