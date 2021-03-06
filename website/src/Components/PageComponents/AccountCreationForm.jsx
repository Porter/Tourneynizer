import React, { Component } from 'react';
import { FormGroup, ControlLabel, FormControl, HelpBlock, Col, Button, Panel } from 'react-bootstrap';
import '../../resources/index.css';

class AccountCreationForm extends Component{
	constructor(props, context) {
		super(props, context);
		this.handleChange = this.handleChange.bind(this);
		this.state = {
			email: '',
			password: '',
			confirmPassword: '',
			firstName: '',
			lastName: '',
			emailValid: false,
			passwordValid: false,
			confirmPasswordValid: false
		};

		this.onSubmit.bind;
	}

	handleChange(e) {
    	this.setState({ [e.target.id]: e.target.value });
  	}

  	getEmailValidationState(){
  		if(this.state.email.indexOf('@') > -1){
  			return 'success';
  			this.setState({emailValid: true})
  		}else{
  			return 'error';
  		}
  	}

  	getPasswordValidationState(){
  		const length = this.state.password.length;
  		if(length > 6 && this.validityTesterHelper(this.state.password)){
  			return 'success';
  			this.setState({passwordValid: true})
  		}else{
  			return 'error';
  		}
  	}

  	getConfirmPasswordValidationState(){
  		const length = this.state.password.length;
  		if(length > 6 && this.validityTesterHelper(this.state.password) && this.state.password === this.state.confirmPassword){
  			return 'success';
  			this.setState({confirmPasswordValid: true})
  		}else{
  			return 'error';
  		}
  	}

  	validityTesterHelper(passwordString){
  		return /\d/.test(passwordString);
  	}

  	onSubmit(e){

  		if(this.state.emailValid && this.state.passwordValid && this.state.confirmPasswordValid && this.state.firstName > 0){
  			console.log("submitting!");
  		}
  		e.preventDefault();
  	}

	render(){
		return(
			<div className='MarginSpacer'>
				<div id="creationForm" className='AccountForm'>
					<form horizontal='true' onSubmit={(e) => this.onSubmit(e)}>
						<FormGroup
							controlId="firstName"
						>
							<Col>
							<FormControl
								type="text"
								value={this.state.firstName}
								placeholder="First Name"
								onChange={this.handleChange}
							/>
							</Col>
						</FormGroup>
						<FormGroup
							controlId="lastName"
						>
							<Col>
							<FormControl
								type="text"
								value={this.state.lastName}
								placeholder="Last Name"
								onChange={this.handleChange}
							/>
							</Col>
						</FormGroup>
						<FormGroup
							controlId="email"
							validationState={this.getEmailValidationState()}
						>
							<Col>
							<FormControl
								type="email"
								value={this.state.email}
								placeholder="Email address"
								onChange={this.handleChange}
							/>
							<FormControl.Feedback />
							<HelpBlock>Email address must be valid</HelpBlock>
							</Col>
						</FormGroup>
						<FormGroup
							controlId="password"
							validationState={this.getPasswordValidationState()}
						>
							<Col>
							<FormControl
								type="password"
								value={this.state.password}
								placeholder="Enter Your Password"
								onChange={this.handleChange}
							/>
							<FormControl.Feedback />
							<HelpBlock>Password must be at least 6 characters and contain at least one number</HelpBlock>
							</Col>
						</FormGroup>
						<FormGroup
							controlId="confirmPassword"
							validationState={this.getConfirmPasswordValidationState()}
						>
							<Col>
							<FormControl
								type="Password"
								value={this.state.confirmPassword}
								placeholder="Re-enter Your Password"
								onChange={this.handleChange}
							/>
							<FormControl.Feedback />
							<HelpBlock>Password must match previously entered password</HelpBlock>
							</Col>
						</FormGroup>
						<Button type="submit">Sign up</Button>
					</form>
				</div>
			</div>
		);
	}
}

export default AccountCreationForm