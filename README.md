# 修改AdapterDelegates开源项目
开源项目地址：https://github.com/sockeqwe/AdapterDelegates

发现该开源项目扩展性似乎有点差，里面没有可以扩展的接口。有点搞不下去了。于是有了重构的想法了,这种想法是不是太邪恶了？

**该项目的设计原理是，每种类型的视图对应一种代理，对adapter起到很好的解耦效果。不管是代码管理还是视图结构都清晰明了。该项目精简，几个类就解决问题，如果只有简单的需要没必要引入复杂的adapter封装包。**

## 所做修改
1. 提取抽象类IDelegate、IDelegateManager，使得项目扩展性更好。
2. 遵守开闭原则，新增了IbbListDelegateAdapter类，用于添加itemClick itemLongClick事件。

## Dependencies
This library is available on maven central:
```
compile 'com.ibbhub.android:adapterdelegate:1.0.0'
```

## 使用说明
### viewType ==1的情况
当视图的类型只有一种的时候，我们的adapter需要继承IbbListDelegateAdapter,该类传入一个List<?>数据。在构造方法里面添加列表视图的代理就行，只有一种视图的时候我们添加AbsFallbackAdapterDelegate的代理类型即可。
**IbbListDelegateAdapter**
```
public class ReptilesAdapter extends IbbListDelegateAdapter<List<DisplayableItem>> {

  public ReptilesAdapter(List<DisplayableItem> items) {

    // Delegates
    this.delegatesManager.setFallbackDelegate(new ReptilesFallbackDelegate());

    setItems(items);
  }
}
```
**AbsFallbackAdapterDelegate**:该代理为默认代理，避免用户设置视图类型的时候出现没有代理对应的情况。如果只有一种视图类型，建议使用该代理，因为该代理默认添加了视图的点击和长按事件。
```
public class ReptilesFallbackDelegate extends AbsFallbackAdapterDelegate<List<DisplayableItem>> {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_unknown_reptile, parent, false);
        return new ReptileFallbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        super.onBindViewHolder(items, position, holder, payloads);
    }

    class ReptileFallbackViewHolder extends RecyclerView.ViewHolder {
        public ReptileFallbackViewHolder(View itemView) {
            super(itemView);
        }
    }
}
```


**设置itemClick**:如果想设置item的点击事件和长按事件，设置监听事件即可。
```
//remember,The adapter must extends AbsFallbackAdapterDelegate and @Override
//public void onBindViewHolder()

adapter.setListener(new AdapterListener<List<DisplayableItem>>() {
      @Override
      public void onClick(List<DisplayableItem> displayableItems, int pos) {
        Toast.makeText(ReptilesActivity.this, "click-->pos:"+pos, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onLongClick(List<DisplayableItem> displayableItems, int pos) {
        Toast.makeText(ReptilesActivity.this, "long click-->pos:"+pos, Toast.LENGTH_SHORT).show();
      }
    });
```

### 2.viewType >1的情况
**ListDelegationAdapter **:如果视图类型为多种，则需要继承ListDelegationAdapter，利用delegatesManager添加多种代理。在Delegate当中设置是否为显示的类型，如果该类型没有代理对应，则显示默认代理的视图。

```
public class AnimalAdapter extends ListDelegationAdapter<List<Animal>> {

  public AnimalAdapter(Activity activity, List<Animal> items) {

    // DelegatesManager is a protected Field in ListDelegationAdapter
    delegatesManager.addDelegate(new CatAdapterDelegate(activity))
                    .addDelegate(new DogAdapterDelegate(activity))
                    .addDelegate(new GeckoAdapterDelegate(activity))
                    .addDelegate(23, new SnakeAdapterDelegate(activity));

    // Set the items from super class.
    setItems(items);
  }
}
```


**AdapterDelegate **:如果视图的类型为多种，则继承该类。
```
public class CatAdapterDelegate extends AdapterDelegate<List<Animal>> {

  private LayoutInflater inflater;

  public CatAdapterDelegate(Activity activity) {
    inflater = activity.getLayoutInflater();
  }

  @Override public boolean isForViewType(@NonNull List<Animal> items, int position) {
    return items.get(position) instanceof Cat;
  }

  @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
    return new CatViewHolder(inflater.inflate(R.layout.item_cat, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull List<Animal> items, int position,
      @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {

    CatViewHolder vh = (CatViewHolder) holder;
    Cat cat = (Cat) items.get(position);

    vh.name.setText(cat.getName());
  }

  static class CatViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    public CatViewHolder(View itemView) {
      super(itemView);
      name = (TextView) itemView.findViewById(R.id.name);
    }
  }
}
```
