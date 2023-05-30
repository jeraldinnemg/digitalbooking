import './Feature.css'

export const icons = [
  { value: 'mono', icon: "🐵" },
  { value: 'serpiente', icon: "🐍" },
  { value: 'escalar', icon: "🧗" },
  { value: 'computadora', icon: "💻" },
];

export const Feature = ({ feature }) => {
  function renderIcon(feature) {
    const icon = icons.find(icon => icon.value === feature);
    return icon ? icon.icon : '';
  }
  return (
    <section className='feature'>
      <h2 className='feature__title'>Experiencia que se ofrecen</h2>
      <ul className='feature__list'>
        {
          feature.map((value, i) => (
            <li key={i} className='feature__item'>
              <span className='item-icon'>{renderIcon(value)}</span>
              <span className='item-description'>{value}</span>
            </li>

          ))
        }
      </ul>
    </section>
  )
}
