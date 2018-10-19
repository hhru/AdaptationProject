import React from 'react';
import {
  Button,
  Collapse,
  Form,
  Navbar,
  NavbarBrand,
  NavbarToggler,
  Nav,
  Container,
  NavLink,
  NavItem,
} from 'reactstrap';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import '!style-loader!css-loader!./Header.css';

const mapStateToProps = (state) => {
  return {
    user: state.user,
  };
};

class Header extends React.Component {
  state = {
    collapsed: true,
  };

  navbarColor = ADAPT_TEST_MODE ? 'dark progress-bar-striped' : 'dark';
  navbarBrandText = ADAPT_TEST_MODE ? 'Adapt_test' : 'Adaptation';

  toggleNavbar = () => {
    this.setState({
      collapsed: !this.state.collapsed,
    });
  };

  render() {
    const { user } = this.props;
    return (
      <div className="header">
        <Navbar color={this.navbarColor} dark expand="md">
          <Container>
            <NavbarBrand tag={Link} to="/">
              {this.navbarBrandText}
            </NavbarBrand>
            <NavbarToggler onClick={this.toggleNavbar} />
            <Collapse isOpen={!this.state.collapsed} navbar>
              <Nav navbar className="mr-auto">
                <NavLink href="/list_employees">Сотрудники</NavLink>
                <NavLink href="/add_employee">Добавить</NavLink>
                {user.isAdmin && <NavLink href="/admin_page">Админка</NavLink>}
              </Nav>
              <Nav>
                <Form action="/api/logout" method="POST">
                  <NavItem>
                    <span className="user-info">Привет, {user.firstName}!</span>
                    <Button type="submit">Выйти</Button>
                  </NavItem>
                </Form>
              </Nav>
            </Collapse>
          </Container>
        </Navbar>
      </div>
    );
  }
}

export default connect(mapStateToProps)(Header);
