import '../App.css';

import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../logoNavbar.png';
import '../css/navbar.css'
import Home from './Home';
import Image from 'react-bootstrap/Image'
import Search from './Search'

class Navbar extends React.Component {
    constructor(props) {
      super(props);

        this.state ={
          search: ''
        }
        this.handleChange = this.handleChange.bind(this)

    }

handleChange(event){
  this.setState({
   [event.target.name]: event.target.value
  })
}
    render(){
     
      return(  
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
              <a  id="image" className="image" href='/'  title="UNQflix home">
                <div className="icon-container style-scope" id="logo-icon-container"> 
                <img id="logo" src={logo} alt="unqflix logo"></img>
               </div>
               </a>
          <form className="form-inline my-2 my-lg-0" name="search" onSubmit={e=> {e.preventDefault()}}>
            <input className="form-control mr-sm-2"
                   type="text" 
                   name="search" 
                   onChange={this.handleChange} 
                   placeholder="Escribe aqui.." 
                   aria-label="Search">  
           </input>
          <Link className="btn btn-primary" type="submit" to={`/search/${this.state.search}`}>Buscar</Link>
          </form>
        </div>
        <div className="user">
        <Link to="/login" className="lonk">Cerrar Sesión</Link>
          </div>
      </nav>
      );
    }
    }

    export default Navbar;