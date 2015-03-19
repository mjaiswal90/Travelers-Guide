;/** @jsx React.DOM */

var TweetWatch = TweetWatch || {};

(function () {

    /** count formatting function, for example for rendering 1.5K followers */
    var numberFormat = function (number) {
        if (number < 1000) { return number; }
        else if (number < 100000) { return (Math.round(number / 100) / 10) + "K"}
        else if (number < 1000000) { return Math.round(number / 1000) + "K"}
        else { return (Math.round(number / 100000) / 10) + "M"}
    };

    /** TimeStamp formatting function, making use of moment.js */
    var fromNow = function (date) {
        var timeString = moment(date).fromNow(true);
        if (timeString === "a few seconds") { return "just now"; }
        else { return timeString; }
    };

   
    var FavoriteCount = React.createClass({displayName: 'FavoriteCount',
        render: function() {
            if(this.props.count > 0) {
                return React.DOM.div( {className:"pull-right timeInterval"}, numberFormat(this.props.count), " fav ")
            }
            else return React.DOM.div(null);
        }
    });

    /** single Tweet component */
    var Tweet = React.createClass({displayName: 'Tweet',
        render: function () {
            var img = "";
            if (this.props.t.entities.media && this.props.t.entities.media.length > 0) {
                img = React.DOM.div( {className:"tweet-image"}, 
                          React.DOM.a( {href:this.props.t.entities.media[0].url, target:"_blank"}, 
                              React.DOM.img( {src:this.props.t.entities.media[0].media_url + ":small"} )
                          )
                      );
            }

            return (
            React.DOM.div( {className:"tweet"}, 
                React.DOM.span(null, 
                    React.DOM.a( {href:"http://www.twitter.com/" + this.props.t.user.screen_name, target:"_blank"}, 
                        React.DOM.img( {className:"thumbnail", src:this.props.t.user.profile_image_url} )
                    )
                ),
                React.DOM.a( {href:"http://www.twitter.com/" + this.props.t.user.screen_name, target:"_blank"}, 
                    React.DOM.span( {className:"username"}, this.props.t.user.name)
                ),
                React.DOM.span( {className:"username_screen"}, " @",this.props.t.user.screen_name),
                React.DOM.div( {className:"pull-right timeInterval"}, fromNow(this.props.t.created_at)),
                React.DOM.div( {className:"tweettext"}, 
                    React.DOM.div( {dangerouslySetInnerHTML:{__html: this.props.t.text}, className:""}),
                    React.DOM.div( {className:"pull-left timeInterval"}, numberFormat(this.props.t.user.followers_count), " followers"),
                    // RetweetCount( {count:this.props.t.retweet_count} ),
                    FavoriteCount( {count:this.props.t.favorite_count} )
                ),
                img,
                React.DOM.div( {className:"intent"}, 
                    React.DOM.a( {href:"https://www.twitter.com/intent/tweet?in_reply_to=" + this.props.t.id_str}, 
                        React.DOM.img( {src:"images/reply.png"})
                    ),
                    React.DOM.a( {href:"https://www.twitter.com/intent/retweet?tweet_id=" + this.props.t.id_str}, 
                        React.DOM.img( {src:"images/retweet.png"})
                    ),
                    React.DOM.a( {href:"https://www.twitter.com/intent/favorite?tweet_id=" + this.props.t.id_str}, 
                        React.DOM.img( {src:"images/favorite.png"})
                    )
                )
            )
        ); }
    });

    /** Tweet list component, renders all Tweet items (above) */
    var TweetList = React.createClass({displayName: 'TweetList',
        render: function() {
            var tweetNodes = this.props.tweets.map(function (tweet, idx, arr) {
                return Tweet( {t:tweet, key:idx} );
            }.bind(this));
            return React.DOM.div( {id:"tweet-list"}, tweetNodes);
        }
    });

      

    /** render TweetWatch components */
    var tweetListComp = React.renderComponent(TweetList( {tweets:[]}), document.getElementById('tweet-frame'));
    
    
    TweetWatch.setTweetList = function (tweetList) { tweetListComp.setProps({tweets: tweetList}); };

    TweetWatch.formatTweet = function (t) {
        if (t.hasOwnProperty('_source')) { t = t._source; }

        var tags = t.entities.hashtags;
        var mentions = t.entities.user_mentions;
        var urls = t.entities.urls;

        t.htmlText = t.text;
        t.htmlText = t.htmlText.replace("RT ", "<strong>RT </strong>");

        for (var i = 0; i < tags.length; i++) {
            t.htmlText = t.htmlText.replace("#" + tags[i].text, "<a href='https://twitter.com/search?q=%23" + tags[i].text +
                " ' target='_blank'>#" + tags[i].text + "</a>");
        }
        for (var j = 0; j < mentions.length; j++) {
            t.htmlText = t.htmlText.replace("@" + mentions[j].screen_name, "<a href='https://twitter.com/" +
                mentions[j].screen_name + " ' target='_blank'>@" + mentions[j].screen_name + "</a>");
        }
        for (var k = 0; k < urls.length; k++) {
            t.htmlText = t.htmlText.replace(urls[k].url, "<a href='" + urls[k].url +
                " ' target='_blank'>" + urls[k].display_url + "</a>");
        }
        return t;
    };
})();