"""Convert Forge's TSRG2 (obf -> mojmap) to Tiny v2 with namespaces
   official, intermediary, named.  Since no real intermediary exists for MC 26.1.2,
   we use the Mojmap names as a stand-in for intermediary -- a self-consistent identity
   that lets fabric-loom set up a dev environment.  The produced jar will not be
   portable to a real Fabric 26.1.2 install, but enables compile and dev runtime."""

import sys, re, pathlib, zipfile, os

if len(sys.argv) != 3:
    sys.exit("usage: tsrg2_to_tiny.py <input.tsrg> <output.tiny>")

src, dst = sys.argv[1], sys.argv[2]
out_lines = ["tiny\t2\t0\tofficial\tintermediary\tnamed"]

def remap_desc(desc, classmap):
    # Replace L<obf>; with L<mojmap>; in JVM descriptors.
    def repl(m):
        name = m.group(1)
        return "L" + classmap.get(name, name) + ";"
    return re.sub(r"L([^;]+);", repl, desc)

# First pass: collect class map
classmap = {}
with open(src, "r", encoding="utf-8") as f:
    header = f.readline()
    if not header.startswith("tsrg2"):
        sys.exit(f"Expected tsrg2 header, got: {header!r}")
    for line in f:
        if not line or line.startswith("\t"):
            continue
        parts = line.rstrip("\n").split(" ")
        if len(parts) >= 2:
            classmap[parts[0]] = parts[1]

# Second pass: emit
with open(src, "r", encoding="utf-8") as f:
    f.readline()  # skip header
    current_class_obf = None
    for line in f:
        if not line.rstrip("\n"):
            continue
        if not line.startswith("\t"):
            parts = line.rstrip("\n").split(" ")
            if len(parts) < 2:
                continue
            obf, mojmap = parts[0], parts[1]
            current_class_obf = obf
            # c <official> <intermediary> <named>
            out_lines.append(f"c\t{obf}\t{mojmap}\t{mojmap}")
        else:
            inner = line.lstrip("\t").rstrip("\n")
            parts = inner.split(" ")
            if len(parts) == 2:
                # field without descriptor (rare in TSRG2 but possible)
                obf, mojmap = parts
                out_lines.append(f"\tf\tI\t{obf}\t{mojmap}\t{mojmap}")
            elif len(parts) == 3:
                # field or method:  <obf> <descriptor> <mojmap>
                obf, desc, mojmap = parts
                mapped_desc = remap_desc(desc, classmap)
                if desc.startswith("("):
                    out_lines.append(f"\tm\t{desc}\t{obf}\t{mojmap}\t{mojmap}")
                else:
                    out_lines.append(f"\tf\t{desc}\t{obf}\t{mojmap}\t{mojmap}")
            # 4+ parts likely parameter mappings — skipped (not required for compile)

pathlib.Path(dst).parent.mkdir(parents=True, exist_ok=True)
with open(dst, "w", encoding="utf-8", newline="\n") as f:
    f.write("\n".join(out_lines))
    f.write("\n")

print(f"Wrote {dst}: {len(out_lines)} lines, {len(classmap)} classes")
