import '../css/home.css';

import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from './Navbar'
import ContentMiniatura from '../ContentMiniatura'
import axios from 'axios'

class Home extends React.Component {
  constructor(props) {
    super(props);

    this.state={
      series: [],
      movies: [],
      favourites: [],
      banners: [],
      lastSeen: [],
      error: '',
    }
  this.handleContent=this.handleContent.bind(this)
}
 
   
  componentDidMount(){
      if(localStorage.getItem('Token')===null) {
        localStorage.setItem('Token', this.props.token)
          }
      var token = localStorage.getItem('Token') 
      
    axios.get('http://localhost:7000/content', { headers:{ Authentication: token }})
      .then(response => this.setState({ 
        series: response.data.filter(content =>{ return content.id.charAt(0) === "s"}),
        movies: response.data.filter(content =>{ return content.id.charAt(0) === "m"})
      }))
      .catch(error => { 
          
        if(error.response.status === 401){
          console.log("y?", this.props.history)
          this.props.history.push("/login")
      }
    })
      axios.get('http://localhost:7000/user', { headers:{ Authentication: token }})
      .then(response=> this.setState({ favourites: response.data.favourites}))

      axios.get('http://localhost:7000/user', { headers:{ Authentication: token }})
      .then(response=> this.setState({ lastSeen: response.data.lastSeen}))
      
      axios.get('http://localhost:7000/banners', { headers:{ Authentication: token }})
      .then(response=> this.setState({ banners: response.data}))
      
  } 

  

  handleContent(content){
    var token = localStorage.getItem('Token') 
    if(content.id.charAt(0) === "s"){
     return (<ContentMiniatura to={`/serie/${content.id}`} key={content.id} content={content} token={token} isFavourite={this.isFavourite(content.id)} />)
    }
    if(content.id.charAt(0) === "m"){
     return (<ContentMiniatura to={`/movie/${content.id}`} key={content.id} content={content} token={token} />)    }
  }

   isFavourite(contentId){
    
    return(
      console.log(`es fav ${contentId}`, this.state.favourites.some(content => content.id === contentId))
      //this.state.favourites.some(content => content.id === contentId)) 
    )
  }
  
 
  render(){ 
    

   
    return(
    <div className="home">

      <div className="favoritos-container">
        <h1>Favoritos</h1>
        <div className="content-container"> 
        {this.state.favourites.map(content=> 
          this.handleContent(content)
          )}
      </div>
      </div>
      
      <div className="lastSeen-container">
        <h1>Ultimos vistos</h1>
        <div className="content-container"> 
        {this.state.lastSeen.map(content=> 
          this.handleContent(content)
          )}
      </div>
      </div>

      <div className="banners-container">
        <h1>Banners</h1>
        <div className="content-container"> 
        {this.state.banners.map(content=> 
          this.handleContent(content)
          )}
      </div>
      </div>

      <div className="series">
        <h1>Series</h1>
        <div className="content-container">
        {this.state.series.map( content => (
            <ContentMiniatura to={`/serie/${content.id} `} key={content.id} content={content} token={localStorage.getItem('Token') } />
            ))}
        </div>
      </div>

      <div className="movies">
          <h1>Peliculas</h1>
          <div className="content-container">
            {this.state.movies.map( content => (
            <ContentMiniatura to={`/movie/${content.id} `} key={content.id} content={content} token={localStorage.getItem('Token') } />
            ))}
          </div> 
      </div>

    </div>
    )
  }
    
 }

    export default Home;