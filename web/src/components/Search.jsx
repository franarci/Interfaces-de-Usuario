import '../css/home.css';

import React from 'react';
import ContentMiniatura from '../ContentMiniatura'

import axios from 'axios'

class Search extends React.Component {
  constructor(props) {
    super(props);

    this.state={
      content: [],
      error: '',
    }
    this.handleContent=this.handleContent.bind(this)
   }

  componentDidMount(){
   console.log("didmount",this.props.match.params)
   axios.get(`http://localhost:7000/search?text=${this.props.match.params.text}`, { headers:{ Authentication: localStorage.getItem('Token') }})
   .then(response => this.setState({ 
        content: response.data
   }))
   .catch(error => { 
     console.log(error)  
 })
  } 
  componentDidUpdate(){
    axios.get(`http://localhost:7000/search?text=${this.props.match.params.text}`, { headers:{ Authentication: localStorage.getItem('Token') }})
    .then(response => this.setState({ 
         content: response.data
    }))
    .catch(error => { 
      console.log(error)  
  })
  }
  handleContent(content){
    var token = localStorage.getItem('Token') 
    if(content.id.charAt(0) === "s"){
     return (<ContentMiniatura to={`/serie/${content.id}`} key={content.id} content={content} token={token}  />)
    }
    if(content.id.charAt(0) === "m"){
     return (<ContentMiniatura to={`/movie/${content.id}`} key={content.id} content={content} token={token} />)    }
  }

  render(){ 
    const {content, error} = this.state

   
    return(
      <div className="Search">
      <div className="results">
        <h1>Resultados de la Búsqueda</h1>
        <div className="content-container">
        {this.state.content.map( content => (
          this.handleContent(content)
            ))}
        </div>
      </div>
   </div>
    )
  }
    
 }

    export default Search;