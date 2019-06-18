import React, { Component } from "react";
import ProjectTask from "./ProjectTasks/ProjectTask";
import { connect } from "react-redux";
import {
  getBacklog,
  updateProjectTaskStatus
} from "../../actions/backlogActions";
import PropTypes from "prop-types";

class Backlog extends Component {
  constructor() {
    super();
    this.state = {
      sortBy: ""
    };
  }

  componentDidMount() {
    this.setState({ sortBy: this.props.sortBy });
  }

  onDragOver(e) {
    e.preventDefault();
  }

  async onDrop(e, newStatus) {
    let project_task = JSON.parse(e.dataTransfer.getData("projectTask"));
    project_task.status = newStatus;
    await this.props.updateProjectTaskStatus(project_task);
    await this.props.getBacklog(
      project_task.projectIdentifier,
      this.props.sortBy
    );
    console.log(this.props.sortBy);
  }

  render() {
    const { project_tasks_prop } = this.props;

    const tasks = project_tasks_prop.map(project_task => (
      <ProjectTask key={project_task.id} project_task={project_task} />
    ));

    let todoItems = [];
    let inProgressItems = [];
    let doneItems = [];

    for (let i = 0; i < tasks.length; i++) {
      //console.log(tasks[i]);

      if (tasks[i].props.project_task.status === "TO_DO") {
        todoItems.push(tasks[i]);
      }

      if (tasks[i].props.project_task.status === "IN_PROGRESS") {
        inProgressItems.push(tasks[i]);
      }

      if (tasks[i].props.project_task.status === "DONE") {
        doneItems.push(tasks[i]);
      }
    }

    return (
      <div className="container">
        <div className="row">
          <div
            className="col-md-4"
            onDragOver={e => this.onDragOver(e)}
            onDrop={e => this.onDrop(e, "TO_DO")}
          >
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {todoItems}
          </div>
          <div
            className="col-md-4"
            onDragOver={e => this.onDragOver(e)}
            onDrop={e => this.onDrop(e, "IN_PROGRESS")}
          >
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inProgressItems}
          </div>
          <div
            className="col-md-4"
            onDragOver={e => this.onDragOver(e)}
            onDrop={e => this.onDrop(e, "DONE")}
          >
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {doneItems}
          </div>
        </div>
      </div>
    );
  }
}

Backlog.propTypes = {
  sortBy: PropTypes.string.isRequired,
  updateProjectTaskStatus: PropTypes.func.isRequired,
  getBacklog: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  sortBy: state.sortBy
});

export default connect(
  mapStateToProps,
  { getBacklog, updateProjectTaskStatus }
)(Backlog);
