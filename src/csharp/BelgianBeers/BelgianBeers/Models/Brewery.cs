using System;

namespace BelgianBeers.Models
{
    public class Brewery
    {
        public Brewery(string name)
        {
            Name = name;
        }
        
        public string Name { get; }

        public void Deconstruct(out string name)
        {
            name = Name;
        }

        protected bool Equals(Brewery other)
        {
            return string.Equals(Name, other.Name, StringComparison.OrdinalIgnoreCase);
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((Brewery) obj);
        }

        public override int GetHashCode()
        {
            return (Name != null ? StringComparer.OrdinalIgnoreCase.GetHashCode(Name) : 0);
        }
    }
}