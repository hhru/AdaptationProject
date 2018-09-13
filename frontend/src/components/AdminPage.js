import React from 'react';
import { Row, Button, Container, Table, FormGroup, Form, Input } from 'reactstrap';
import axios from 'axios';

class AdminPage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      data: [],
    };

    this.ruleList = this.ruleList.bind(this);
    this.toggleEnv = this.toggleEnv.bind(this);
  }

  ruleList(e) {
    const url = '/api/rule_list';
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        self.setState({
          data: response.data ? response.data : [],
          showUsers: false,
          showRules: true,
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  toggleEnv() {
    if (ADAPT_TEST_MODE) {
      document.cookie = 'adaptation_session=';
    } else {
      document.cookie = 'adaptation_session=_TEST_';
    }

    location.reload();
  }

  render() {
    return (
      <Container>
        <Row>
          <Button color="secondary" outline onClick={this.ruleList}>
            Список правил
          </Button>
          <Button color="secondary" outline onClick={this.toggleEnv} className="ml-4">
            {ADAPT_TEST_MODE ? 'Перейти на прод' : 'Перейти на стенд'}
          </Button>
        </Row>

        <RuleTable rules={this.state.data} />
      </Container>
    );
  }
}

class RuleTable extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      hhidValue: '',
      accessValue: '',
    };

    this.onAccessChange = this.onAccessChange.bind(this);
    this.onHhidChange = this.onHhidChange.bind(this);
    this.newRule = this.newRule.bind(this);
    this.realSubmit = this.realSubmit.bind(this);
  }

  onAccessChange(e) {
    this.setState({
      accessValue: e.target.value,
    });
  }

  onHhidChange(e) {
    this.setState({
      hhidValue: e.target.value,
    });
  }

  realSubmit(hhid, access) {
    const url = '/api/new_rule';
    const self = this;

    axios
      .post(url, {
        hhid: hhid,
        accessType: access,
      })
      .then(function(response) {
        self.setState({
          hhidValue: '',
          accessValue: '',
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  newRule(e) {
    e.preventDefault();
    if (!this.state.hhidValue || !this.state.accessValue) return;
    this.realSubmit(this.state.hhidValue, this.state.accessValue);
  }

  render() {
    return (
      <Table>
        <thead>
          <tr>
            <th>id</th>
            <th>hhid</th>
            <th>Name</th>
            <th>Email</th>
            <th>Access type</th>
            <th>cmd</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td />
            <td>
              <Input
                rows="1"
                type="text"
                name="text"
                placeholder="123"
                onChange={this.onHhidChange}
                value={this.state.hhidValue}
              />
            </td>
            <td />
            <td />
            <td>
              <Input
                rows="1"
                type="text"
                name="text"
                placeholder="OTHER"
                onChange={this.onAccessChange}
                value={this.state.accessValue}
              />
            </td>
            <td>
              <Button color="secondary" outline onClick={this.newRule}>
                добавить
              </Button>
            </td>
          </tr>
          {this.props.rules.map((rule) => (
            <RuleTableItem
              id={rule.id}
              hhid={rule.hhid}
              firstName={rule.firstName}
              lastName={rule.lastName}
              email={rule.email}
              accessType={rule.accessType}
              key={rule.id}
            />
          ))}
        </tbody>
      </Table>
    );
  }
}

class RuleTableItem extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      cmdValue: '',
      accessType: props.accessType,
    };

    this.cmdSubmit = this.cmdSubmit.bind(this);
    this.onCmdChange = this.onCmdChange.bind(this);
    this.realSubmit = this.realSubmit.bind(this);
  }

  onCmdChange(e) {
    this.setState({
      cmdValue: e.target.value,
    });
  }

  realSubmit(newRole) {
    const url = '/api/update_access/' + this.props.hhid;
    const self = this;

    axios
      .post(url, newRole)
      .then(function() {
        self.setState({
          accessType: newRole.toUpperCase(),
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  cmdSubmit(e) {
    e.preventDefault();
    if (this.state.cmdValue === '') return;
    this.realSubmit(this.state.cmdValue);
    this.setState({
      cmdValue: '',
    });
  }

  render() {
    return (
      <tr>
        <td>{this.props.id}</td>
        <td>{this.props.hhid}</td>
        <td>{this.props.firstName + ' ' + this.props.lastName}</td>
        <td>{this.props.email}</td>
        <td>{this.state.accessType}</td>
        <td>
          <Form onSubmit={(e) => this.cmdSubmit(e)}>
            <FormGroup>
              <Input
                rows="1"
                type="text"
                name="text"
                placeholder="OTHER"
                onChange={this.onCmdChange}
                value={this.state.cmdValue}
              />
            </FormGroup>
          </Form>
        </td>
      </tr>
    );
  }
}

export default AdminPage;
