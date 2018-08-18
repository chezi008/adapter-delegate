# 修改AdapterDelegates开源项目
开源项目地址：https://github.com/sockeqwe/AdapterDelegates

发现该开源项目扩展性似乎有点差，里面没有可以扩展的接口。有点搞不下去了。于是有了重构的想法了,这种想法是不是太邪恶了？

## 所做修改
1. 提取抽象类IDelegate、IDelegateManager，使得项目扩展性更好。
2. 遵守开闭原则，新增了IbbListDelegateAdapter类，用于添加itemClick itemLongClick事件。

## Dependencies
This library is available on maven central:
```
compile 'com.ibbhub.android:adapterdelegate:1.0.0'
```

## 使用说明
### 1.viewType ==1的情况
**xxDelegate extends AbsFallbackAdapterDelegate**
```
public class ReptilesFallbackDelegate extends AbsFallbackAdapterDelegate<List<DisplayableItem>> {

    private LayoutInflater inflater;

    public ReptilesFallbackDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

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
**xxAdapter extends IbbListDelegateAdapter**
```
public class ReptilesAdapter extends IbbListDelegateAdapter<List<DisplayableItem>> {

  public ReptilesAdapter(Activity activity, List<DisplayableItem> items) {

    // Delegates
    this.delegatesManager.setFallbackDelegate(new ReptilesFallbackDelegate(activity));

    setItems(items);
  }
}
```
**设置itemClick**
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

**xxDelegate extends AdapterDelegate**
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
**xxAdapter extends ListDelegationAdapter**

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
