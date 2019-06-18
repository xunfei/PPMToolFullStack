import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logout, nameChange } from "../../actions/securityActions";
import { NavDropdown } from "react-bootstrap";

class Header extends Component {
  constructor() {
    super();
    this.state = {
      changedName: ""
    };
    this.onChangeNameClick = this.onChangeNameClick.bind(this);
  }
  logout() {
    this.props.logout();
    window.location.href = "/";
  }

  onChangeNameClick(e) {
    const newName = window.prompt("Enter new name: ");
    if (newName) {
      this.props.nameChange(newName);
      this.setState({ changedName: newName });
    }
  }

  render() {
    const { validToken, user } = this.props.security;

    const userIsAuthenticated = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link className="nav-link" to="/dashboard">
              Dashboard
            </Link>
          </li>
        </ul>

        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <NavDropdown
              title={this.state.changedName || user.fullName}
              id="basic-nav-dropdown"
            >
              <NavDropdown.Item onClick={this.onChangeNameClick}>
                Change Name
              </NavDropdown.Item>
            </NavDropdown>
          </li>
          <li className="nav-item">
            <Link
              className="nav-link"
              to="/logout"
              onClick={this.logout.bind(this)}
            >
              Logout
            </Link>
          </li>
        </ul>
      </div>
    );

    const userIsNotAuthenticated = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link className="nav-link" to="/register">
              Sign Up
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/login">
              Login
            </Link>
          </li>
        </ul>
      </div>
    );

    let headerLinks;

    if (validToken && user) {
      headerLinks = userIsAuthenticated;
    } else {
      headerLinks = userIsNotAuthenticated;
    }

    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container">
          <Link className="navbar-brand" to="/">
            Personal Project Management Tool
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#mobile-nav"
          >
            <span className="navbar-toggler-icon" />
          </button>
          {headerLinks}
        </div>
      </nav>
    );
  }
}

Header.propTypes = {
  nameChange: PropTypes.func.isRequired,
  logout: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  { logout, nameChange }
)(Header);
