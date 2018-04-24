import React from 'react';
import {
  Col,
  Button,
  Form,
  FormGroup,
  Label,
  Input,
  FormText,
  Container,
} from 'reactstrap';
import axios from 'axios';
import ReactDOM from 'react-dom';

class AddEmployee extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selfFirstName: '',
      selfLastName: '',
      selfMiddleName: '',
      selfEmail: '',

      chiefFirstName: '',
      chiefLastName: '',
      chiefMiddleName: '',
      chiefEmail: '',

      hrId: 1,
      gender: 'MALE',
      position: '',
      employmentDate: '',
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleInputChange(event) {
    const input = event.target;
    const value = input.type === 'checkbox' ? input.checked : input.value;
    const name = input.name;

    this.setState({
      [name]: value,
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    let body = {
      self: {
        firstName: this.state.selfFirstName,
        lastName: this.state.selfLastName,
        middleName: this.state.selfMiddleName,
        email: this.state.selfEmail,
      },
      chief: {
        firstName: this.state.chiefFirstName,
        lastName: this.state.chiefLastName,
        middleName: this.state.chiefMiddleName,
        email: this.state.chiefEmail,
      },
      gender: this.state.gender,
      hrId: this.state.hrId,
      position: this.state.position,
      employmentDate: this.state.employmentDate,
    };
    console.log(body);
    const url = '/api/employee/create';
    const self = this;
    axios
      .post(url, body)
      .then(function(response) {
        alert('Пользователь успешно создан');
        self.props.history.push('/employee/' + response.data.id);
      })
      .catch(function(error) {
        console.log(error);
        alert(error);
      });
    // const url = '/api/employee/';
    // let data = this.state;
    // //$.post(url, data, function (response) {
    // //}.bind(this));
    // let { firstName, lastName } = this.state;
    // alert(`Сотрудник ${firstName} ${lastName} добавлен в систему`);
  }

  render() {
    return (
      <Container>
        <Form>
          <FormGroup row>
            <h3>Сотрудник</h3>
          </FormGroup>
          <LabeledInputFormElement
            labelText="Имя"
            name="selfFirstName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.selfFirstName}
          />
          <LabeledInputFormElement
            labelText="Отчество"
            name="selfMiddleName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={false}
            value={this.state.selfMiddleName}
          />
          <LabeledInputFormElement
            labelText="Фамилия"
            name="selfLastName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.selfLastName}
          />
          <LabeledInputFormElement
            labelText="Должность"
            name="position"
            type="text"
            onChange={this.handleInputChange}
            isRequired={false}
            value={this.state.position}
          />
          <LabeledInputFormElement
            labelText="Email"
            name="selfEmail"
            type="email"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.selfEmail}
          />
          <LabeledInputFormElement
            labelText="Дата выхода"
            name="employmentDate"
            type="date"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.employmentDate}
          />

          <FormGroup row>
            <h3>Начальник</h3>
          </FormGroup>
          <LabeledInputFormElement
            labelText="Имя"
            name="chiefFirstName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.chiefFirstName}
          />
          <LabeledInputFormElement
            labelText="Отчество"
            name="chiefMiddleName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={false}
            value={this.state.chiefMiddleName}
          />
          <LabeledInputFormElement
            labelText="Фамилия"
            name="chiefLastName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.chiefLastName}
          />
          <LabeledInputFormElement
            labelText="Email"
            name="chiefEmail"
            type="email"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.chiefEmail}
          />
          <Button onClick={this.handleSubmit}>Создать</Button>
        </Form>
      </Container>
    );
  }
}

class InputFormElement extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { name, type, value, onChange, required } = this.props;
    return (
      <input
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        required={required}
      />
    );
  }
}

class LabeledInputFormElement extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {
      name,
      type,
      value,
      onChange,
      isRequired: required,
      labelText,
    } = this.props;
    return (
      <FormGroup row>
        <Label for={name} sm={2}>
          {labelText}
        </Label>
        <Col sm={10}>
          <Input
            type={type}
            name={name}
            id={name}
            onChange={onChange}
            value={value}
          />
        </Col>
      </FormGroup>
    );
  }
}

export default AddEmployee;
