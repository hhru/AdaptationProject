import React from 'react';
import ReactDOM from 'react-dom';
import {
  Button,
  Collapse,
  Container,
  Form,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
} from 'reactstrap';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { connect } from 'react-redux';
import axios from 'axios';

import { setInitialized, setLoggedIn } from '../actions/index';

const mapStateToProps = (state) => {
  return { loggedIn: state.loggedIn };
};

const mapDispatchToProps = (dispatch) => {
  return {
    setInitialized: () => dispatch(setInitialized()),
    setLoggedIn: () => dispatch(setLoggedIn()),
  };
};

class UserInfo extends React.Component {
  constructor(props) {
    super(props);

    let self = this;

    axios
      .get('/api/user')
      .then(function(response) {
        self.setState({
          userName: response.data.firstName,
        });
        self.props.setLoggedIn();
        self.props.setInitialized();
      })
      .catch(function(error) {
        self.props.setInitialized();
      });
  }

  render() {
    if (this.props.loggedIn)
      return (
        <NavItem className="mr-3">
          <NavLink className="text-white" tag={Link} to="#">
            Привет, {this.state.userName}!
          </NavLink>
        </NavItem>
      );
    else return null;
  }
}

const ConnectedUserInfo = connect(mapStateToProps, mapDispatchToProps)(UserInfo);

class LoginLogoutButton extends React.Component {
  render() {
    return (
      <NavItem className="mr-3">
        <Form action={this.props.loggedIn ? '/api/logout' : '/api/login'} method="POST">
          <Button className="text-white" type="submit">
            {this.props.loggedIn ? 'Выйти' : 'Войти'}
          </Button>
        </Form>
      </NavItem>
    );
  }
}

const ConnectedLoginLogoutButton = connect(mapStateToProps)(LoginLogoutButton);

class NavButtons extends React.Component {
  render() {
    if (this.props.loggedIn)
      return (
        <Nav className="mr-auto" navbar>
          <NavItem className="ml-3">
            <Button className="text-white" tag={Link} to="/list_employees">
              Список сотрудников
            </Button>
          </NavItem>
          <NavItem className="ml-3">
            <Button className="text-white" tag={Link} to="/add_employee">
              Добавить сотрудника
            </Button>
          </NavItem>
        </Nav>
      );
    else return null;
  }
}

const ConnectedNavButtons = connect(mapStateToProps)(NavButtons);

class Header extends React.Component {
  render() {
    return (
      <Navbar color="dark" dark expand="md" className="mb-3">
        <Container>
          <NavbarBrand className="ml-4 text-white" tag={Link} to="/">
            Adaptation
          </NavbarBrand>
          <ConnectedNavButtons />
          <Nav className="ml-auto" navbar>
            <ConnectedUserInfo />
            <ConnectedLoginLogoutButton />
          </Nav>
        </Container>
      </Navbar>
    );
  }
}

export default Header;
