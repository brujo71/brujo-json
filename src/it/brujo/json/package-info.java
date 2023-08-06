/**
 *  Classes are structed as follow:<br>
 *  <pre><code>	{ 
 *  		JSonLabel : JSonElem,
 *  	  	JSonLabel : JSonElem,	
 *   	  	JSonLabel : JSonElem
 *   	}</code></pre>
 *  or
 *  <pre><code> [ JSonElem, JSonElem, JSonElem, JSonElem ] </code></pre>
 *  Class hierarchy is as follow:
 *  <pre>
 *  JSonElem ---+--- JSonArray 
 *              |
 *              +--- JSonObj 
 *              |
 *              +--- JSonValue ----- JSonConstant
 *                               |
 *                               +-- JSonNumber
 *                               |
 *                               +-- JSonString                 
 *  </pre>
 */
package it.brujo.json;