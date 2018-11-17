import React from 'react';
import { FormGroup, Label, Input } from 'reactstrap';
import '!style-loader!css-loader!../app.css';
import DatePicker from 'react-datepicker';
import moment from 'moment';
import '!style-loader!css-loader!react-datepicker/dist/react-datepicker.css';

class TaskCalendar extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      deadlineDate: moment(),
      deadlineWeeks: '',
    };
  }

  componentDidMount() {
    if (this.props.isWeeks) {
      this.setState({ deadlineWeeks: this.props.deadline });
    } else {
      this.setState({ deadlineDate: moment(this.props.deadline, 'DD.MM.YYYY') });
    }
  }

  dateHandler = (date) => {
    this.setState({
      deadlineDate: date,
    });
    this.props.onChange(date.format('DD.MM.YYYY'));
  };

  radioHandler = () => {
    let value = this.props.isWeeks
      ? this.state.deadlineDate.format('DD.MM.YYYY')
      : this.state.deadlineWeeks;
    this.props.onChange(value, !this.props.isWeeks);
  };

  weeksHandler = (event) => {
    let value = event.target.value;
    this.setState({ deadlineWeeks: value });
    this.props.onChange(value, true);
  };

  render() {
    return (
      <FormGroup tag="fieldset">
        <FormGroup check>
          <Label check>
            <Input
              type="radio"
              name={this.props.number}
              checked={!this.props.isWeeks}
              onChange={this.radioHandler}
            />Выбрать дату
            <DatePicker
              dateFormat="DD/MM/YYYY"
              selected={this.state.deadlineDate}
              onChange={this.dateHandler}
              className="chief-input"
            />
          </Label>
        </FormGroup>

        <FormGroup check>
          <Label check>
            <Input
              type="radio"
              name={this.props.number}
              checked={this.props.isWeeks}
              onChange={this.radioHandler}
            />Указать количество недель
            <input
              type="text"
              name="text"
              onChange={this.weeksHandler}
              value={this.state.deadlineWeeks}
              className="chief-input"
            />
          </Label>
        </FormGroup>
      </FormGroup>
    );
  }
}

export default TaskCalendar;
