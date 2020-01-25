# moxy-strategy-plugin

This plugin automates work with [moxy](https://github.com/moxy-community/Moxy) framework strategies 

You can download it here https://plugins.jetbrains.com/plugin/13679-moxy-strategy/versions

## Inspector
This plugin adds inspector which checks function in intefaceses which implement MvpView.

If function doesn't have any strategy, function will be highlighted and will be suggested add one of strategy.

When adding a strategy, imports will be added too

![](https://github.com/Maksim-Novikov/moxy-strategy-plugin/blob/master/media/add_strategy.gif)

When adding the AddToEndSingleTagStrategy, cursore will be moved to "tag" argument


![](https://github.com/Maksim-Novikov/moxy-strategy-plugin/blob/master/media/add_tag_strategy.gif)


## Intention

This plugin adds intention which allows you replace current strategy.

When adding a strategy, imports will be added too

![](https://github.com/Maksim-Novikov/moxy-strategy-plugin/blob/master/media/replace_strategy.gif)

When replacing to the AddToEndSingleTagStrategy, cursore will be moved to "tag" argument

![](https://github.com/Maksim-Novikov/moxy-strategy-plugin/blob/master/media/replace_tag_strategy.gif)
