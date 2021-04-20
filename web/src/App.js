import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react';
import axios from 'axios'
import {BrowserRouter, Switch, Route} from 'react-router-dom';

import Login from './components/Login.jsx';
import Home from './components/Home.jsx';
import Register from './components/Register';
import Movie from './components/Movie';
import Player from './components/Player';
import Serie from './components/Serie';
import Navbar from './components/Navbar';
import Search from './components/Search';

 class App extends React.Component {
  constructor(props){
      super(props);
      this.state = {
        loggedIn: false,
        token: ''
      }
      this.handleLogin = this.handleLogin.bind(this)
     
    }
  
handleLogin(data){
  this.setState({
    token: data,
    loggedIn: true
  })
  console.log("token en app",this.state)
}

 render(){
  return (
    <BrowserRouter>
     <Navbar {...this.props} token= {this.state.token} />
      <Switch>
        <Route exact path="/" render ={ props => (
          <Home {...props} loggedIn= {this.state.loggedIn} token={this.state.token}/>
           )} /> 
        <Route exact path="/login" render ={props => (
          <Login {...props} loggedIn= {this.state.loggedIn} handleLogin={this.handleLogin} /> )}/>   
        <Route exact path= "/register" render={props => (
          <Register {...props} loggedIn={this.state.loggedIn} handleLogin={this.handleLogin} /> )}/>
        <Route path="/serie/:idserie" render={props => (
          <Serie {...props} loggedIn={this.state.loggedIn} token={this.state.token} />)} />
        <Route path="/movie/:idmovie" component={Movie}></Route>
        <Route path="/player/:id"  render={props => (
          <Player {...props} loggedIn={this.state.loggedIn} token={this.state.token} />)} />
        <Route path="/search/:text" render={props => (
          <Search {...props} token={this.state.token} /> )} />
        <Route path="*" render= {() => <h1>Not Found</h1>} />
      </Switch>
    </BrowserRouter>
    )
  }
}
export default App;