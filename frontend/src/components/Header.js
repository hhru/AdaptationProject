import React from 'react';
import { Button, Container, Form, Navbar, NavbarBrand, Nav, NavItem, NavLink } from 'reactstrap';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import '!style-loader!css-loader!./Header.css';

const mapStateToProps = (state) => {
  return {
    user: state.user,
  };
};

class UserInfo extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    if (this.props.user)
      return (
        <NavItem className="mr-3">
          <NavLink className="text-white" tag={Link} to="#">
            Привет, {this.props.user.firstName}!
          </NavLink>
        </NavItem>
      );
    else return null;
  }
}

const ConnectedUserInfo = connect(mapStateToProps)(UserInfo);

class LoginLogoutButton extends React.Component {
  render() {
    const hidden = this.props.user
      ? false
      : this.props.path === '/' || this.props.path === '/donate';
    return (
      !hidden && (
        <NavItem className="mr-3">
          <Form action={this.props.user ? '/api/logout' : '/api/login'} method="POST">
            <Button className="text-white" type="submit">
              {this.props.user ? 'Выйти' : 'Войти'}
            </Button>
          </Form>
        </NavItem>
      )
    );
  }
}

const ConnectedLoginLogoutButton = connect(mapStateToProps)(LoginLogoutButton);

class NavButtons extends React.Component {
  render() {
    if (this.props.user)
      return (
        <Nav className="mr-auto" navbar>
          <NavItem className="ml-3">
            <Button
              className="text-white borderless-button"
              outline
              tag={Link}
              to="/list_employees"
            >
              Список сотрудников
            </Button>
          </NavItem>
          <NavItem className="ml-3">
            <Button className="text-white borderless-button" outline tag={Link} to="/add_employee">
              Добавить сотрудника
            </Button>
          </NavItem>
          {this.props.user.isAdmin && (
            <NavItem className="ml-3">
              <Button className="text-white borderless-button" outline tag={Link} to="/admin_page">
                Админка
              </Button>
            </NavItem>
          )}
        </Nav>
      );
    else return null;
  }
}

const ConnectedNavButtons = connect(mapStateToProps)(NavButtons);

class Header extends React.Component {
  render() {
    let navbarColor = 'dark';
    let navbarBrandText = 'Adaptation';

    if (ADAPT_TEST_MODE) {
      navbarColor = 'dark progress-bar-striped';
      navbarBrandText = 'Adapt_test';
    }

    return (
      <Navbar color={navbarColor} dark expand="md" className="mb-3">
        <Container>
          <NavbarBrand className="ml-4 text-white" tag={Link} to="/">
            {navbarBrandText}
          </NavbarBrand>
          <ConnectedNavButtons />
          <Nav className="ml-auto" navbar>
            <ConnectedUserInfo />
            <ConnectedLoginLogoutButton path={this.props.location.pathname} />
          </Nav>
        </Container>
      </Navbar>
    );
  }
}

export default Header;
