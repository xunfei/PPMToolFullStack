import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getBacklog, updateSort } from "../../actions/backlogActions";
import { Dropdown } from "react-bootstrap";

class ProjectBoard extends Component {
  //constructor to handle errors
  constructor() {
    super();
    this.state = {
      sortBy: "",
      errors: {}
    };
    this.onSortActionClick = this.onSortActionClick.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id, this.state.sortBy);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onSortActionClick(e, sortBy) {
    const { id } = this.props.match.params;
    this.setState({ sortBy: sortBy });
    this.props.updateSort(sortBy);
    this.props.getBacklog(id, sortBy);
  }

  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;

    const { errors } = this.state;

    let BoardContent;

    const boardAlgorithm = (errors, project_tasks) => {
      if (project_tasks.length < 1) {
        if (errors.projectNotFound) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectNotFound}
            </div>
          );
        } else if (errors.projectIdentifier) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectIdentifier}
            </div>
          );
        } else {
          return (
            <div className="alert alert-info text-center" role="alert">
              No Project Tasks on this board
            </div>
          );
        }
      } else {
        return (
          <Backlog
            project_tasks_prop={project_tasks}
            sortBy={this.state.sortBy}
          />
        );
      }
    };

    BoardContent = boardAlgorithm(errors, project_tasks);

    //fix the bug where people can access the createPT page on an invalid project
    let CreatePTButton;
    const createPTButtonAlg = errors => {
      if (errors.projectNotFound || errors.projectIdentifier) {
        return (
          <button className="btn btn-primary mb-3" disabled>
            <i className="fas fa-plus-circle"> Create Project Task</i>
          </button>
        );
      } else {
        return (
          <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
            <i className="fas fa-plus-circle"> Create Project Task</i>
          </Link>
        );
      }
    };
    CreatePTButton = createPTButtonAlg(errors);

    return (
      <div className="container">
        {CreatePTButton}
        <br />
        <Dropdown>
          <Dropdown.Toggle variant="outline-primary" id="dropdown-basic">
            Sort By:
          </Dropdown.Toggle>

          <Dropdown.Menu>
            <Dropdown.Item onClick={e => this.onSortActionClick(e, "0")}>
              Priority
            </Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item onClick={e => this.onSortActionClick(e, "1")}>
              Due Date
            </Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item onClick={e => this.onSortActionClick(e, "2")}>
              Project Sequence
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <hr />
        {BoardContent}
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  updateSort: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  backlog: state.backlog,
  errors: state.errors,
  sortBy: state.sortBy
});

export default connect(
  mapStateToProps,
  { getBacklog, updateSort }
)(ProjectBoard);
