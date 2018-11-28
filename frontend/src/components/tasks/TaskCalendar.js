import React from 'react';
import { FormGroup, Label, Input } from 'reactstrap';
import '!style-loader!css-loader!../app.css';
import DatePicker from 'react-datepicker';
import moment from 'moment';
import '!style-loader!css-loader!react-datepicker/dist/react-datepicker.css';

class TaskCalendar extends React.Component {
  state = {
    deadlineDate: moment(),
    deadlineWeeks: '',
  };

  componentDidMount() {
    const { isWeeks, deadline } = this.props;
    if (isWeeks) {
      this.setState({ deadlineWeeks: deadline });
    } else {
      this.setState({ deadlineDate: moment(deadline, 'DD.MM.YYYY') });
    }
  }

  dateHandler = (date) => {
    this.setState({
      deadlineDate: date,
    });
    this.props.onChange(date.format('DD.MM.YYYY'));
  };

  radioHandler = () => {
    const value = this.props.isWeeks
      ? this.state.deadlineDate.format('DD.MM.YYYY')
      : this.state.deadlineWeeks;
    this.props.onChange(value, !this.props.isWeeks);
  };

  weeksHandler = (event) => {
    const value = event.target.value;
    this.setState({ deadlineWeeks: value });
    this.props.onChange(value, true);
  };

  render() {
    const { number, isWeeks } = this.props;
    const { deadlineDate, deadlineWeeks } = this.state;
    return (
      <FormGroup tag="fieldset">
        <FormGroup check>
          <Label check>
            <Input type="radio" name={number} checked={!isWeeks} onChange={this.radioHandler} />Выбрать
            дату
            <DatePicker
              dateFormat="DD/MM/YYYY"
              selected={deadlineDate}
              onChange={this.dateHandler}
              className="form-control"
            />
          </Label>
        </FormGroup>

        <FormGroup check>
          <Label check>
            <Input type="radio" name={number} checked={isWeeks} onChange={this.radioHandler} />Указать
            количество недель
            <input
              type="text"
              name="text"
              onChange={this.weeksHandler}
              value={deadlineWeeks}
              className="form-control"
            />
          </Label>
        </FormGroup>
      </FormGroup>
    );
  }
}

export default TaskCalendar;
