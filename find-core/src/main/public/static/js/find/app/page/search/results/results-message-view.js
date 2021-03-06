/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

define([
    'jquery',
    'backbone',
    'underscore',
    'find/app/util/view-server-client',
    'find/app/util/viewing-colourbox',
    'find/app/vent',
    'i18n!find/nls/bundle',
    'text!find/templates/app/page/search/results/results-message-view.html',
    'bootstrap',
    'colorbox'
], function($, Backbone, _, viewClient, viewingColourbox, vent, i18n, template) {
    "use strict";

    return Backbone.View.extend({
        template: _.template(template),

        events: {
            'click .service-view-back-button': function() {
                vent.navigate(this.backUrl);
            }
        },

        initialize: function(options) {
            this.queryModel = options.queryModel;
            this.backUrl = options.backUrl;
        },

        render: function() {
            var document = this.queryModel.get('document');
            var reference = document.get('reference');

            var href = viewClient.getHref(reference, document);

            this.$el.html(this.template({
                i18n: i18n,
                message: i18n['search.results.message.similarDocuments'],
                title: document.get('title'),
                href: href
            }));

            viewingColourbox.fancyButtonOverride();

            var colorboxOptions = {model: document, href: href, grouping: 'reference-document'};

            var $titleNode = this.$('.reference-document-title');

            viewingColourbox.nearNativeOrTab(colorboxOptions, reference, $titleNode);

            return this;
        }
    })
});