import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery';


class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: false,
        };

        this.handleLoginLogout = this.handleLoginLogout.bind(this);
    }
  
    handleLoginLogout () {
        var url = '/api/login/';
        var data = this.state;
//        $.get(url, data, function (response) {
//            console.log(response);
            this.setState(
                prevState => ({
                    isLoggedIn: !prevState.isLoggedIn
                })
            );
            this.props.history.push('/home');
//        }.bind(this));
    }

    render() {
        return (
            <div>
                <p>
                    Welcome to adaptation homepage!
                </p>
                
                <button onClick={this.handleLoginLogout}>
                    {this.state.isLoggedIn ? 'Logout' : 'Login'}
                </button>
 
 
            </div>
        );
    }
}


export default Home;

