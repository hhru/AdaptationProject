import React from 'react';
import ReactDOM from 'react-dom';

class AddEmployee extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: '',
      middleName: '',
      lastName: '',
      gender: '',
      position: '',
      email: '',
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
    const url = '/api/employee/';
    let data = this.state;
    //$.post(url, data, function (response) {
    //}.bind(this));
    let { firstName, lastName } = this.state;
    alert(`Сотрудник ${firstName} ${lastName} добавлен в систему`);
  }

  render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <LabeledInputFormElement
            labelText="Имя"
            name="firstName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.firstName}
          />
          <LabeledInputFormElement
            labelText="Отчество"
            name="middleName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={false}
            value={this.state.middleName}
          />
          <LabeledInputFormElement
            labelText="Фамилия"
            name="lastName"
            type="text"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.lastName}
          />
          <span>
            <label>
              Пол:
              <InputFormElement
                name="gender"
                type="radio"
                value="male"
                onChange={this.handleInputChange}
                required={true}
              />
              М
              <InputFormElement
                name="gender"
                type="radio"
                value="female"
                onChange={this.handleInputChange}
                required={true}
              />
              Ж
            </label>
            <br />
          </span>
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
            name="email"
            type="email"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.email}
          />
          <LabeledInputFormElement
            labelText="Дата выхода"
            name="employmentDate"
            type="date"
            onChange={this.handleInputChange}
            isRequired={true}
            value={this.state.employmentDate}
          />
          <input type="submit" value="Submit" />
        </form>
      </div>
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
      <span>
        <label>
          {labelText + ':'}
          <InputFormElement
            name={name}
            type={type}
            value={value}
            onChange={onChange}
            required={required}
          />
        </label>
        <br />
      </span>
    );
  }
}

export default AddEmployee;
